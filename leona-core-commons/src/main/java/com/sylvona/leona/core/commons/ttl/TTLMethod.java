package com.sylvona.leona.core.commons.ttl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TTLMethod {
    long value();
    TimeUnit unit() default TimeUnit.SECONDS;
}
