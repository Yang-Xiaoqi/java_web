package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class MyAspect1 {

    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    private void pt(){}

    // 前置通知，目标方法运行之前通知1
    @Before("pt()")
    public void before(){
        log.info("before...");
    }
    // 环绕通知，目标方法运行之前之后都会运行
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("around before...");
        Object result =pjp.proceed();
        log.info("around after...");
        return result;
    }
    // 后置通知，目标方法运行之后通知,无论是否异常
    @After("pt()")
    public void after(){
        log.info("after...");
    }

    // 后置通知，目标方法运行之后通知,如果出现异常不会执行
    @AfterReturning("pt()")
    public void afterReturning(){
        log.info("afterReturning...");
    }
    // 异常后通知，目标方法运行之后通知,出现异常才会执行
    @AfterThrowing("pt()")
    public void afterThrowing(){
        log.info("afterThrowing...");
    }
}
