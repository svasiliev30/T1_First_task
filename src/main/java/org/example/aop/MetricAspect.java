package org.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.dao.entity.Metric;
import org.example.kafka.KafkaProducerMetricError;
import org.example.service.metric.TargetMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Aspect
@Component
public class MetricAspect {

    private static final AtomicLong START_TIME = new AtomicLong();

    @Autowired
    KafkaProducerMetricError kafkaProducerMetricError;

    @Autowired
    Metric metric;

    @Pointcut("@annotation(targetMetric)")
    public void metricMethods(TargetMetric targetMetric) {
    }

    @Before("metricMethods(targetMetric)")
    public void logExecTime(JoinPoint joinPoint, TargetMetric targetMetric) {
        log.info("---------------- Start method: {}", joinPoint.getSignature().toShortString());
        START_TIME.addAndGet(System.currentTimeMillis());
    }

    @After("metricMethods(targetMetric)")
    public void calculateTime(JoinPoint joinPoint, TargetMetric targetMetric) {
        long afterTime = System.currentTimeMillis();
        Long result = afterTime - START_TIME.get();
        log.info("Time work: {} ms", result);
        START_TIME.set(0L);
    }

    @Around("metricMethods(targetMetric)")
    public void logExecTime(ProceedingJoinPoint pJoinPoint, TargetMetric targetMetric) throws Throwable {
        log.info("Get method: {}", pJoinPoint.getSignature().toShortString());
        long startTime = System.currentTimeMillis();

        try {
            Object proceed = pJoinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long resultTime = endTime - startTime;
            if (resultTime > targetMetric.timeLimitMillis()) {
                String nameMethod = getMethod(pJoinPoint).toString();
                metric.setTime(resultTime);
                metric.setNameMethod(nameMethod);
                metric.setParam(Arrays.toString(pJoinPoint.getArgs()));
                log.warn("The execution time is excellent - {}, name Class - {}", resultTime, nameMethod);
                kafkaProducerMetricError.send(metric, "METRICS");
            }
        }
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

}