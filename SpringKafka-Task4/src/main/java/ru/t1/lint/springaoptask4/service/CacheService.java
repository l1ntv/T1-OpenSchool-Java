package ru.t1.lint.springaoptask4.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.t1.lint.springaoptask4.model.CacheEntry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheService {

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private final long ttlMillis;

    public CacheService(@Value("${settings.ttl_in_s}") long ttlSeconds) {
        this.ttlMillis = ttlSeconds * 1000;
    }

    public Object get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.getValue();
        }
        cache.remove(key);
        return null;
    }

    public void put(String key, Object value) {
        cache.put(key, new CacheEntry(value, System.currentTimeMillis() + ttlMillis));
    }

    public String generateKey(String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder(methodName);
        for (Object arg : args) {
            sb.append(":").append(arg == null ? "null" : arg.toString());
        }
        return sb.toString();
    }
}
