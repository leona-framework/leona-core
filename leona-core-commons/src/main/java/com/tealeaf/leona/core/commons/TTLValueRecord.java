package com.tealeaf.leona.core.commons.aop;

import java.time.Instant;

public record TTLValueRecord<T>(T value, Instant expiration) implements TTLValue<T> {
}
