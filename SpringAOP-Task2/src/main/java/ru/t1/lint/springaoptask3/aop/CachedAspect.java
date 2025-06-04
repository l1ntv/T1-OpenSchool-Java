package ru.t1.lint.springaoptask3.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask3.service.CacheService;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Order(1)
public class CachedAspect {

    private final CacheService cacheService;

    @Pointcut("@annotation(ru.t1.lint.springaoptask3.aop.Cached)")
    public void cachedMethod() {

    }

    @Around("cachedMethod()")
    public Object checkCache(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();

        String key = cacheService.generateKey(methodName, args);

        Object result = cacheService.get(key);
        if (result == null) {
            result = joinPoint.proceed();
            cacheService.put(key, result);
        }

        return result;
    }
}
