package com.example.HMS.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// Tracks how much time each method takes — and also issues warnings for slow methods.

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Around("execution(* com.example.HMS.service.*.*(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable
    {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - start;

        log.info("Performance : {}.{}() executed in {} ms",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                timeTaken);

        // SLA breach warning
        if (timeTaken > 500) {
            log.warn("Slow Method : {}.{}() took {}ms - exceeds 500ms SLA threshold",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    timeTaken);
        }

        return result;
    }
}
