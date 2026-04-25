package com.example.HMS.aop;

import com.example.HMS.anotations.Auditable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable{
        String action = auditable.action();
        String startTime = LocalDateTime.now().toString();
        String methodName = joinPoint.getSignature().getName();

        log.info("[AUDIT-START] Action={} | Method={} | Time={} ",  // | Args={}
                action, methodName, startTime);
//                Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        log.info("[AUDIT-SUCCESS] Action={} | Method={} | CompletedAt={}",
                action, methodName, LocalDateTime.now());

        return result;
    }
}
