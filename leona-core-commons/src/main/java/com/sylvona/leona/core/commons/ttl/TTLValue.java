package com.sylvona.leona.core.commons.ttl;

import java.time.Instant;

/**
 * Represents a cached value along with its associated expiration time for Time-to-Live (TTL) caching.
 *
 * @param <T> The type of the cached value.
 */
public interface TTLValue<T> {

    /**
     * Retrieves the cached value.
     *
     * @return The cached value.
     */
    T value();

    /**
     * Retrieves the instant at which the cached value is set to expire.
     *
     * @return The expiration instant.
     */
    Instant expiration();

    /**
     * Checks if the cached value has expired based on the current time.
     *
     * @return {@code true} if the cached value has expired, {@code false} otherwise.
     */
    default boolean isExpired() {
        return Instant.now().isAfter(expiration());
    }
}
