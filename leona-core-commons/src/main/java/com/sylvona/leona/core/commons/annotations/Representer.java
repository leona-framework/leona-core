package com.sylvona.leona.core.commons.annotations;

import com.sylvona.leona.core.commons.containers.Tuple;
import com.sylvona.leona.core.commons.streams.LINQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationConfigurationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility class that works with the {@link Represents @Represents} annotation to create an object-based representation of a runtime annotation.
 * @see Represents
 */
public final class Representer {
    private static final Map<Class<? extends Annotation>, Representer> ANNOTATION_REPRESENTATION_MAP = new HashMap<>();
    private static final Map<Class<?>, Map<Class<? extends Annotation>, Representer>> CLASS_TO_ANNOTATION_MAP = new HashMap<>();

    private final List<Tuple<Method, Field>> mappedAnnotationFields = new ArrayList<>();
    private final Class<?> representationClass;
    private final Constructor<?> representationCtor;
    private Field annotationTargetField;

    private Representer(Class<? extends Annotation> annotationClass, Class<?> representationClass, boolean classDriven) {
        this.representationClass = representationClass;
        try {
            this.representationCtor = representationClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Could not find empty constructor for class %s".formatted(representationClass), e);
        }

        Map<String, Field> allFieldsMap = new HashMap<>();
        for (Field field : representationClass.getDeclaredFields()) {
            // Get the field mapping annotation on the class' fields
            Represents.FieldMapping fieldMappingAnnotation = field.getAnnotation(Represents.FieldMapping.class);
            // If this extractor is being driven by an annotation on the representation class, set the field name to the value of the annotation
            String fieldNaming = classDriven && fieldMappingAnnotation != null && !StringUtils.isBlank(fieldMappingAnnotation.value()) ? fieldMappingAnnotation.value() : field.getName();
            allFieldsMap.put(fieldNaming, field);

            Represents.SourceTargetField targetFieldAnnotation = field.getAnnotation(Represents.SourceTargetField.class);
            if (targetFieldAnnotation == null) continue;

            if (annotationTargetField != null)
                throw new IllegalStateException("Representation class cannot have more than one SourceTargetField annotations.");
            annotationTargetField = field;
            field.setAccessible(true);
        }

        for (Method method : annotationClass.getDeclaredMethods()) {
            Represents.FieldMapping fieldMappingAnnotation = method.getAnnotation(Represents.FieldMapping.class);
            String fieldMappingName = null;
            if (fieldMappingAnnotation != null && !classDriven) {
                if (fieldMappingAnnotation.ignore()) continue;
                fieldMappingName = fieldMappingAnnotation.value();
            }

            if (StringUtils.isBlank(fieldMappingName)) {
                fieldMappingName = method.getName();
            }

            Field mappedField = allFieldsMap.get(fieldMappingName);
            if (mappedField == null) continue;
            mappedField.setAccessible(true);

            mappedAnnotationFields.add(new Tuple<>(method, mappedField));
        }
    }

    /**
     * Creates a representation of the provided annotation using its provided representation class.
     *
     * @param annotation The annotation to create a representation for.
     * @param <T>        The type of the representation.
     * @return A representation of the annotation.
     * @throws NoRepresentationException if the passed annotation is not annotated with @Represents OR if its @Represents annotation does not define a representation class.
     * @throws AnnotationConfigurationException if the passed annotation defines more than one representation class.
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Annotation annotation) {
        Class<? extends Annotation> annotationClass = annotation.getClass();
        Representer representation = ANNOTATION_REPRESENTATION_MAP.get(annotationClass);
        if (representation != null) return representation.createRepresentation(annotation);

        Represents representAnnotation = annotation.getClass().getAnnotation(Represents.class);
        if (representAnnotation == null || representAnnotation.value().length == 0) throw new NoRepresentationException("Cannot create a representation for annotation %s. Annotation is not marked with @Represents!".formatted(annotation));
        Class<?>[] annotationToClassRepresentations = representAnnotation.value();
        if (annotationToClassRepresentations.length > 1) throw new AnnotationConfigurationException("@Represents declared for annotations cannot have more than one representation class.");

        return (T) getAndRegisterRepresentation(annotation, annotationClass, annotationToClassRepresentations[0]);
    }

    /**
     * Creates a representation of the provided annotation using the specified representation class.
     * Classes passed in via the {@code representationClass parameter} do not need to have the {@link Represents}
     * annotation present on them, Similarly, the {@code annotation parameter} does not need that annotation present either.
     * However, if the annotation is present on either input, the mappings provided via that annotation will be used to enhance the final
     * object. In the case that both inputs have the {@code Represents} annotation present on them, the annotation on the {@code representationClass} will
     * take priority and will have its field mappings used.
     *
     * @param annotation         The annotation to create a representation for.
     * @param representationClass The class to use as the representation.
     * @param <T>                The type of the representation.
     * @return A representation of the annotation.
     */
    public static <T> T get(Annotation annotation, Class<T> representationClass) {
        return getAndRegisterRepresentation(annotation, annotation.getClass(), representationClass);
    }

    private static <T> T getAndRegisterRepresentation(Annotation annotation, Class<? extends Annotation> annotationClass, Class<T> representationClass) {
        Represents representationClassAnnotation = representationClass.getAnnotation(Represents.class);
        if (representationClassAnnotation != null && LINQ.stream(representationClassAnnotation.value()).contains(annotationClass)) {
            Map<Class<? extends Annotation>, Representer> classExtractionMap = CLASS_TO_ANNOTATION_MAP.computeIfAbsent(representationClass, ignored -> new HashMap<>());
            return classExtractionMap.computeIfAbsent(annotationClass, ignored -> new Representer(annotationClass, representationClass, true)).createRepresentation(annotation);
        }

        Representer extractor = ANNOTATION_REPRESENTATION_MAP.get(annotationClass);
        if (extractor != null && extractor.representationClass.equals(representationClass)) {
            return extractor.createRepresentation(annotation);
        }

        Representer representer = new Representer(annotationClass, representationClass, false);
        if (extractor == null) { // Don't override the annotation-declared representations
            ANNOTATION_REPRESENTATION_MAP.put(annotationClass, representer);
        }
        return representer.createRepresentation(annotation);
    }

    /**
     * Creates a new instance of the representation class using the values of the provided annotation.
     * @param annotation the annotation to create a representation of
     * @return a representation of the provided annotation
     * @param <T> the type of the representation.
     */
    public <T> T createRepresentation(Annotation annotation) {
        T representation = constructRepresentation();
        for (Tuple<Method, Field> methodFieldTuple : mappedAnnotationFields) {
            try {
                methodFieldTuple.item2().set(representation, methodFieldTuple.item1().invoke(annotation));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        if (annotationTargetField != null) {
            try {
                annotationTargetField.set(representation, annotation);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return representation;
    }

    private <T> T constructRepresentation() {
        try {
            return (T) representationCtor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
