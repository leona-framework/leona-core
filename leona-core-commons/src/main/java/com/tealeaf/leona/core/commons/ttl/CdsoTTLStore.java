package com.tealeaf.leona.core.commons.ttl;

import jakarta.annotation.Nullable;
import org.aspectj.lang.reflect.MethodSignature;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Complexity-Driven Safe Object Map (TTL Store)
 * Implements the basic functionality of {@link TTLStore<T>} using a {@link ComplexityDrivenSafeObjectMap<T>}.
 */
class CdsoTTLStore<T> implements TTLStore<T> {
    private final ComplexityDrivenSafeObjectMap<Map<Integer, TTLValue<T>>> objectMap = new ComplexityDrivenSafeObjectMap<>();

    @Override
    public @Nullable TTLValue<T> fetchValue(Object source, MethodSignature signature) {
        Map<Integer, TTLValue<T>> signatureMap = objectMap.get(source);
        return signatureMap != null ? signatureMap.get(signatureHash(signature)) : null;
    }

    @Override
    public TTLValue<T> storeValue(Object source, MethodSignature signature, T value, Duration lifetime) {
        TTLValue<T> ttlValue = new TTLValueRecord<>(value, Instant.now().plus(lifetime));
        return objectMap.computeIfAbsent(source, o -> new HashMap<>()).put(signatureHash(signature), ttlValue);
    }

    private int signatureHash(MethodSignature signature) {
        return Objects.hash(signature.getMethod(), Arrays.hashCode(signature.getParameterTypes()));
    }
}
