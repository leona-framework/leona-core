package com.sylvona.leona.core.commons.ttl;

import jakarta.annotation.Nullable;
import org.aspectj.lang.reflect.MethodSignature;

import java.time.Duration;

/**
 * A contract for managing Time-to-Live (TTL) caching of method invocations and their results.
 * Implementations of this interface handle the fetching and storing of cached values with associated expiration time.
 *
 * @param <T> The type of values being cached.
 */
public interface TTLStore<T> {

    /**
     * Fetches a {@link TTLValue<T>} based on the invoking object and method, obtained via a point-cut interception.
     *
     * @param source    The object intercepted by the point-cut.
     * @param signature The method signature intercepted by the point-cut.
     * @return A cached {@link TTLValue<T>} or null if not present.
     */
    @Nullable TTLValue<T> fetchValue(Object source, MethodSignature signature);

    /**
     * Stores the result of a method's invocation for caching with a specified expiration time.
     *
     * @param source    The invoking object.
     * @param signature The invoked method signature.
     * @param value     The value returned by the invoked method.
     * @param lifetime  A {@link Duration} until the returned value expires and should be fetched again.
     * @return A previously associated {@link TTLValue<T>} or null if none existed.
     */
    TTLValue<T> storeValue(Object source, MethodSignature signature, T value, Duration lifetime);
}

