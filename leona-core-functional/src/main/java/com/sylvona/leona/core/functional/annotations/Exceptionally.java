package com.sylvona.leona.core.functional.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Exceptionally {
    Class<? extends Throwable>[] value() default Throwable.class;
}
