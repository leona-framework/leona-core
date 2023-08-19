package com.tealeaf.leona.core.commons;

import jakarta.annotation.Nullable;
import org.aspectj.lang.reflect.MethodSignature;

import java.time.Duration;

public interface TTLStore<T> {
    /**
     * Fetches a {@link TTLValue<T>} based on the invoking object and method (obtained via point-cut)
     * @param source the object intercepted by the point-cut
     * @param signature the method signature intercepted by the point-cut
     * @return a cached {@link TTLValue<T>} or null if not present.
     */
    @Nullable TTLValue<T> fetchValue(Object source, MethodSignature signature);

    /**
     * Stores the result of a method's invocation for caching
     * @param source the invoking object
     * @param signature the invoked method signature
     * @param value the value returned by the invoked method
     * @param lifetime a {@link Duration} until the returned value expires, and should be fetched again
     * @return a previously associated {@link TTLValue<T>} or null if none existed
     */
    TTLValue<T> storeValue(Object source, MethodSignature signature, T value, Duration lifetime);
}
