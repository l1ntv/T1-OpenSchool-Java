package ru.t1.lint.springaoptask2.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask2.model.TimeLimitExceedLog;
import ru.t1.lint.springaoptask2.repository.TimeLimitExceedLogRepository;

@Aspect
@Component
@RequiredArgsConstructor
@Order(2)
public class MetricAspect {

    private final TimeLimitExceedLogRepository timeLimitExceedLogRepository;

    @Value("${settings.max_time_method_execution_in_ms}")
    private long maxTimeMethodExecution;

    @Pointcut("@annotation(ru.t1.lint.springaoptask2.aop.Metric)")
    public void metricMethod() {

    }

    @Around("metricMethod()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        String signature = joinPoint.getSignature().toString();
        long executionTime = System.currentTimeMillis() - start;

        if (executionTime > maxTimeMethodExecution) {
            TimeLimitExceedLog timeLimitExceedLog = new TimeLimitExceedLog();
            timeLimitExceedLog.setExecutionTime(executionTime);
            timeLimitExceedLog.setMethodSignature(signature);
            timeLimitExceedLog.setMaxExecutionTime(maxTimeMethodExecution);
            timeLimitExceedLogRepository.save(timeLimitExceedLog);
        }

        return result;
    }
}
