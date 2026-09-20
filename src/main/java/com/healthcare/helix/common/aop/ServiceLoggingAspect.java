package com.healthcare.helix.common.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceLoggingAspect {

    /* As of now using only for Login Module */
    @Around("execution(* com.healthcare.helix.authModule.service..*(..))")
    public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().toShortString();

        log.info(">>>> Auth Module Service started: {}", methodName);

        //  return null; if not use proceed(); and actual service will not execute.
        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        log.info("<<< Auth Module Service completed: {} | time={}ms", methodName, executionTime);

        return result;

        /* Internal working Flow
           @Component
             ↓
           Spring Component Scan
             ↓
           ServiceLoggingAspect Bean
             ↓
          @Aspect detected
             ↓
          @Around pointcut applied
             ↓
          AuthModule methods proxied
        * */
    }
}
