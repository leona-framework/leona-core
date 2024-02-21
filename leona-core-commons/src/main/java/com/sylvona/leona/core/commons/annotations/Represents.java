package com.sylvona.leona.core.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to establish a relationship between annotations and Java objects or between classes and annotations.
 * <p> 
 * When used on an annotation, it indicates classes that model the data contained by the annotated annotation.
 * When used on a class, it should contain the annotation classes that the current annotated class models.
 *
 * @author Evan Cowin
 * @since 0.0.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface Represents {
    /**
     * If this is annotation is present on another annotation, contains classes which model the data contained by the current annotated annotation.
     * Otherwise, if present on a class, should contain the annotation classes that the current annotated class models.
     * @return Classes which model the current annotated element.
     */
    Class<?>[] value();

    /**
     * An optional annotation declared on a method or field which maps the annotated element to a specific field or method in a representation class / annotation.
     * <p><p>
     * When this annotation is present on a method in an annotation it will map the value of the annotated method to the field(s) specified by the annotation.
     * <p><p>
     * When present on a field in a class, will map any matching method to the given field. If multiple methods are specified, the first matched method will be mapped.
     * <p>
     * Alternatively, allows for a field/method to be excluded in the mapping process.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.FIELD})
    @interface FieldMapping {
        /**
         * <b>Functionality</b><p>
         * <b>Annotations</b> - the name of fields in a class to map to.<p>
         * <b>Classes</b> - the name of methods in an annotation to map.
         * @return The name of methods or fields to map to
         */
        String[] value() default {};

        /**
         * When true excludes the annotated field / method from the mapping process. Defaults to false.
         * @return true if the annotated element should be ignored mapping, otherwise false.
         */
        boolean excluded() default false;
    }

    /**
     * Optional annotation put on a field in a representation class, that gets populated with the annotation that created the representation.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface SourceTargetField { }
}
