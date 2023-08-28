package com.sylvona.leona.core.commons.ttl;

import java.time.Instant;

/**
 * A record class representing a cached value along with its associated expiration time for Time-to-Live (TTL) caching.
 *
 * @param <T> The type of the cached value.
 */
public record TTLValueRecord<T>(T value, Instant expiration) implements TTLValue<T> {
}
