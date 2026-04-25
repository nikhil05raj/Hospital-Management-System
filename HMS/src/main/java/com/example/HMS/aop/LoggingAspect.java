package com.example.HMS.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// It automatically logs before and after every service method, without touching the service class.

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Intercepts all service layer methods
    @Pointcut("execution(* com.example.HMS.service.*.*(..))")
    public void serviceLayer() {}

    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint)
    {
        log.info(">>> METHOD START : {}.{}()",          // | ARGS : {}
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName());
//                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterSuccess(JoinPoint joinPoint, Object result)
    {
        log.info("<<< METHOD SUCCESS : {}.{}() | RETURNED : {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                result);
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex)
    {
        log.error("!!! Method Failed : {}.{}() | EXCEPTION : {} | MESSAGE : {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }

}
