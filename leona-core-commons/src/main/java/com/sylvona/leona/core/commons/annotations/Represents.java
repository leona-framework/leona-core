package com.sylvona.leona.core.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that allows for the definition of class(es) to be used as standard representations of annotations.
 * This annotation facilitates the consolidation of multiple annotations into a common class, simplifying development.
 * It can be applied in two ways: either on other annotations or on classes themselves.
 *
 * <p>When applied to other annotations, it designates a single class to serve as the "representation" of that annotation.
 * This functionality works in tandem with the {@link Representer#get(Annotation)} method, enabling the dynamic creation of objects
 * based on the representation class associated with a provided annotation. Notably, annotations annotated with {@code @Represents}
 * can specify only one class as a representation.
 *
 * <p>When applied to classes, this annotation enables the specification of multiple annotations that a single class can represent.
 * This facilitates the generation of representation classes through the {@link Representer#get(Annotation, Class)} method,
 * though it is not mandatory when creating representations using this method.
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
         * If this annotation is applied to an annotation method, it specifies the target field of a representation class to use
         * as the target for the method's return value. If applied to a class, it defines the annotation method to map to the
         * targeted field.
         *
         * <p>If no value is defined, mapping occurs based on the name of the field/method.
         *
         * @return The name of the annotation method or field to be used as a target for mapping.
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
