package com.sylvona.leona.core.aop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method-level annotation which enables more robust-error handling in the form of return results.
 * <p>
 * More specifically, when this annotation is present on a method with one of the following return types:
 * <p>
 * {@code Either<T, Throwable>}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Exceptionally {
    Class<? extends Throwable>[] value() default Throwable.class;
}
