package com.example.HMS.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionTrackingAspect {

    @AfterThrowing(
            pointcut = "execution(* com.example.HMS.controllers.*.*(..) )",
            throwing = "ex"
    )
    public void trackException(JoinPoint joinPoint, Exception ex)
    {
        log.error("========== EXCEPTION REPORT ==========");
        log.error("Controller : {}", joinPoint.getTarget().getClass().getSimpleName());
        log.error("Method     : {}", joinPoint.getSignature().getName());
        log.error("Exception  : {}", ex.getClass().getSimpleName());
        log.error("Message    : {}", ex.getMessage());
        log.error("======================================");
    }

}
