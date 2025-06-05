package ru.t1.lint.springaoptask3.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask3.kafka.KafkaMetricProducer;
import ru.t1.lint.springaoptask3.model.TimeLimitExceedLog;
import ru.t1.lint.springaoptask3.repository.TimeLimitExceedLogRepository;

@Aspect
@Component
@RequiredArgsConstructor
@Order(2)
public class MetricAspect {

    private final TimeLimitExceedLogRepository timeLimitExceedLogRepository;
    private final KafkaMetricProducer<TimeLimitExceedLog> kafkaMetricProducer;

    @Value("${settings.max_time_method_execution_in_ms}")
    private long maxTimeMethodExecution;

    private static final String TOPIC = "t1_demo_metrics";

    private static final String HEADER_KEY = "error_type";
    private static final String HEADER_VALUE = "METRICS";

    @Pointcut("@annotation(ru.t1.lint.springaoptask3.aop.Metric)")
    public void metricMethod() {
    }

    @Around("metricMethod()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            String signature = joinPoint.getSignature().toString();
            long executionTime = System.currentTimeMillis() - start;

            if (executionTime > maxTimeMethodExecution) {
                TimeLimitExceedLog logEntry = new TimeLimitExceedLog();
                logEntry.setExecutionTime(executionTime);
                logEntry.setMethodSignature(signature);
                logEntry.setMaxExecutionTime(maxTimeMethodExecution);

                try {
                    kafkaMetricProducer.sendWithHeader(TOPIC, HEADER_KEY, HEADER_VALUE, logEntry);
                } catch (Exception e) {
                    timeLimitExceedLogRepository.save(logEntry);
                }
            }
        }
        return result;
    }
}
