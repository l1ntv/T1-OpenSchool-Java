package ru.t1.lint.springaoptask3.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask3.kafka.KafkaDataSourceErrorProducer;
import ru.t1.lint.springaoptask3.model.DataSourceErrorLog;
import ru.t1.lint.springaoptask3.repository.DataSourceErrorLogRepository;

import java.io.PrintWriter;
import java.io.StringWriter;

@Aspect
@Component
@RequiredArgsConstructor
@Order(3)
public class LogDataSourceError {

    private final DataSourceErrorLogRepository repository;

    private final KafkaDataSourceErrorProducer<DataSourceErrorLog> kafkaDataSourceErrorProducer;

    @Value("${settings.log_data_source_error_topic}")
    private String TOPIC;

    @Value("${settings.log_data_source_error_header_key}")
    private String HEADER_KEY = "error_type";

    @Value("${settings.log_data_source_error_header_value}")
    private String HEADER_VALUE = "DATA_SOURCE";

    private static final Logger logger = LoggerFactory.getLogger(LogDataSourceError.class);

    @Pointcut("@annotation(ru.t1.lint.springaoptask3.aop.DataSourceErrorLoggable)")
    public void loggableMethod() {

    }

    @AfterThrowing(pointcut = "loggableMethod()", throwing = "exception")
    public void logAfterException(JoinPoint joinPoint, Exception exception) {
        String message = exception.getMessage();
        String stackTrace = convertStackTraceToString(exception);
        String methodSignature = joinPoint.getSignature().toShortString();

        logger.error("Exception in method: {} | Message: {}", methodSignature, message);

        DataSourceErrorLog errorLog = new DataSourceErrorLog();
        errorLog.setMessage(message);
        errorLog.setStacktraceText(stackTrace);
        errorLog.setMethodSignature(methodSignature);

        try {
            kafkaDataSourceErrorProducer.sendTo(TOPIC, HEADER_KEY, HEADER_VALUE, errorLog);
        } catch (Exception e) {
            repository.save(errorLog);
        }
    }

    private String convertStackTraceToString(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
