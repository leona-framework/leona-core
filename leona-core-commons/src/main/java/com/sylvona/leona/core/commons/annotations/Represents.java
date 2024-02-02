package com.sylvona.leona.core.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface Represents {
    Class<?>[] value();

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @interface FieldMapping {
        String value() default "";

        boolean ignore() default false;
    }

    /**
     * Optional annotation put on a field in a representation class, that gets populated with the annotation that created the representation.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface SourceTargetField { }
}
