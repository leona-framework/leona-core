package com.tealeaf.leona.core.commons;

import java.time.Instant;

public interface TTLValue<T> {
    T value();

    Instant expiration();

    default boolean isExpired() {
        return Instant.now().isAfter(expiration());
    }
}
