package com.sylvona.leona.core.commons.ttl;

import java.time.Instant;

public record TTLValueRecord<T>(T value, Instant expiration) implements TTLValue<T> {
}
