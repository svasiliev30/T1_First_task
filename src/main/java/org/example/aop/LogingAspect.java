package org.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.dao.entity.DataSourceErrorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogingAspect {

    @Autowired
    DataSourceErrorLog dataSourceErrorLog;

    @Pointcut("execution(public * org.example.service..*(..))")
    public void clientServiceMethods() {
    }

    @Before("clientServiceMethods()" )
    public void beforeCallMethod(JoinPoint joinPoint) {
        log.info("-------------before--------- " + joinPoint.getSignature().getName());
    }

    @After("clientServiceMethods()")
    public void afterCallMethod(JoinPoint joinPoint) {
        log.info("-------------after--------- " + joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "clientServiceMethods()", throwing = "excep")
    public void throwingServiseMethod(JoinPoint joinPoint, Exception excep) {
        log.error("-------------Error----------" + joinPoint.getSignature().getName());
        dataSourceErrorLog.allNull();
        dataSourceErrorLog.setExceptionStackTraceText(Arrays
                .stream(excep.getStackTrace())
                .findFirst()
                .toString());
        dataSourceErrorLog.setMessage(excep.getMessage());
        dataSourceErrorLog.setMethodSignature(joinPoint.getSignature().toString());
    }
}
