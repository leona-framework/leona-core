package com.sylvona.leona.core.commons.ttl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Marks a method and the objects it produces as Time-to-Live (TTL) objects. This annotation allows for the caching of
 * produced objects for a specified amount of time before automatically refreshing the cached object after its expiration time.
 *
 * <p>When applied to a method, the resulting objects from that method will be cached with a Time-to-Live. This means
 * that once an object is retrieved from the method's result, it will be stored in a cache, and future calls to the method
 * within the specified time frame will return the cached object instead of re-executing the method. After the time period
 * defined in the TTL expires, the next call to the method will re-execute the method and refresh the cache with the new result.
 *
 * <p>The TTL value is specified in conjunction with the {@link #unit()} parameter, which determines the time unit used for
 * the TTL duration. By default, the time unit is set to {@link TimeUnit#SECONDS}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TTLMethod {

    /**
     * Specifies the duration of the Time-to-Live (TTL) for the cached objects produced by the annotated method.
     *
     * @return The TTL duration for the cached objects.
     */
    long value();

    /**
     * Specifies the time unit used for interpreting the TTL duration value. The default time unit is seconds.
     *
     * @return The time unit for interpreting the TTL duration.
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
