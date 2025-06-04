package ru.t1.lint.springaoptask3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CacheEntry {

    private final Object value;

    private final long expirationTime;

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
}
