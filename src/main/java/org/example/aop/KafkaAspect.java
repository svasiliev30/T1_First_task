package org.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.dao.entity.DataSourceErrorLog;
import org.example.dao.repository.DataSourceErrorLogRepository;
import org.example.kafka.produser.KafkaProducerMetricError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class KafkaAspect {

    @Autowired
    DataSourceErrorLog dataSourceErrorLog;

    @Autowired
    DataSourceErrorLogRepository repository;

    @Autowired
    KafkaProducerMetricError kafkaProducerMetricError;

    @Pointcut("execution(public * org.example.service..*(..))")
    public void clientServiceMethods() {
    }

    @Pointcut("execution(public * org.example.kafka..*(..))")
    public void clientKafkaMethods() {
    }

    @Pointcut("clientKafkaMethods() || clientServiceMethods()")
    public void clientAll() {
    }

    @Before("clientKafkaMethods()" )
    public void beforeCallMethodKafka(JoinPoint joinPoint) {
        log.info("-------------beforeKafka--------- {} --------------------",
                joinPoint.getSignature().getName());
    }

    @After("clientKafkaMethods()")
    public void afterCallMethodKafka(JoinPoint joinPoint) {
        log.info("-------------afterKafka--------- {} --------------------",
                joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "clientAll()", throwing = "excep")
    public void throwingKafkaMethod(JoinPoint joinPoint, Exception excep) {
        log.error("-------------Error----------" + joinPoint.getSignature().getName());
        dataSourceErrorLog.allNull();
        dataSourceErrorLog.setExceptionStackTraceText(Arrays
                .stream(excep.getStackTrace())
                .findFirst()
                .toString());
        dataSourceErrorLog.setMessage(excep.getMessage());
        dataSourceErrorLog.setMethodSignature(joinPoint.getSignature().toString());
        try {
            kafkaProducerMetricError.send(dataSourceErrorLog,"DATA_SOURCE");
        } catch (Exception e) {
            repository.save(dataSourceErrorLog);
        }
    }
}
