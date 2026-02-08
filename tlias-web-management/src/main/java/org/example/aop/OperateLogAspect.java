package org.example.aop;

import org.example.mapper.OperateLogMapper;
import org.example.pojo.OperateLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.utils.CurrentHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志记录切面
 */
@Aspect
@Component
@Slf4j
public class OperateLogAspect {



    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 切入点：只拦截带有 @Log 注解的方法（更精准控制）
     */
    @Around("@annotation(org.example.anno.Log)")
    public Object recordOperateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        boolean success = false;

        try {
            result = joinPoint.proceed();
            success = true;
            return result;
        } finally {
            // 无论成功或异常，都记录日志（可根据需求调整）
            try {
                OperateLog operateLog = buildOperateLog(joinPoint, result, success, startTime);
                operateLogMapper.insert(operateLog);
                log.info("操作日志记录成功: 类={}, 方法={}",
                        operateLog.getClassName(),
                        operateLog.getMethodName());
            } catch (Exception ex) {
                log.error("操作日志持久化失败", ex);
            }
        }
    }

    /**
     * 构建操作日志对象（不使用 JSON，避免序列化问题）
     */
    private OperateLog buildOperateLog(ProceedingJoinPoint joinPoint, Object result, boolean success, long startTime) {
        OperateLog log = new OperateLog();

        // 操作人ID（请根据你的认证机制替换）
        Integer empId = getCurrentUserId();
        log.setOperateEmpId(empId);


        log.setOperateTime(LocalDateTime.now());
        log.setClassName(joinPoint.getTarget().getClass().getName());
        log.setMethodName(joinPoint.getSignature().getName());

        // 方法参数：直接转字符串（过滤非业务参数）
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            // 过滤掉 HttpServletRequest 等 Web 相关对象
            Object[] filtered = Arrays.stream(args)
                    .filter(arg -> !(arg instanceof HttpServletRequest))
                    .toArray();
            log.setMethodParams(filtered.length > 0 ? Arrays.toString(filtered) : "");
        } else {
            log.setMethodParams("void");
        }

        // 返回值：简单转字符串（避免 null 和异常）
        if (result != null) {
            log.setReturnValue(result.toString());
        } else {
            log.setReturnValue(success ? "null" : "方法执行异常");
        }

        log.setCostTime(System.currentTimeMillis() - startTime);
        return log;
    }

    private Integer getCurrentUserId(){
        return CurrentHolder.getCurrentId();
    }
}