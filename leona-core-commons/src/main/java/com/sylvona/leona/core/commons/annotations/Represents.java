package com.sylvona.leona.core.commons.annotations;

import java.lang.annotation.*;

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
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface Represents {
    /**
     * If annotated on an annotation, specifies the <b>singular</b> class to be used as a representation of this annotation.
     * If annotated on a class, defines an array of annotation classes that the source class can represent.
     *
     * @return A singular class or an array of classes that may represent or be represented by the source.
     */
    Class<?>[] value();

    /**
     * Maps an annotation's method to a field on a target class. This annotation can be used on both an annotation's methods
     * and a representation class' fields.
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
        String value() default "";

        /**
         * This property only affects annotations. If set to true, it skips the annotated method when creating a representation.
         * By default, this property is false.
         *
         * @return True if this method should be skipped when creating a representation.
         */
        boolean ignore() default false;
    }

    /**
     * An optional annotation that can be placed on a field in a representation class. This field will be populated with
     * the annotation that created the representation.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface SourceTargetField {
    }
}
