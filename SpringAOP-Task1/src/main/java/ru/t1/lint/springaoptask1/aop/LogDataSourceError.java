package ru.t1.lint.springaoptask1.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask1.model.DataSourceErrorLog;
import ru.t1.lint.springaoptask1.repository.DataSourceErrorLogRepository;

import java.io.PrintWriter;
import java.io.StringWriter;

@Aspect
@Component
@RequiredArgsConstructor
public class LogDataSourceError {

    private final DataSourceErrorLogRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(LogDataSourceError.class);

    @Pointcut("@annotation(ru.t1.lint.springaoptask1.aop.DataSourceErrorLoggable)")
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

        repository.save(errorLog);
    }

    private String convertStackTraceToString(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
