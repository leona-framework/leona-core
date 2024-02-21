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
 * The main class responsible for creating representation instances of related annotations. It provides methods for
 * retrieving a representation instance for a given annotation and also acts as the blueprint for representation classes.
 *
 * @see Representer#get(Annotation, Class)
 * @see Representer#get(Annotation)
 * @author Evan Cowin
 * @since 0.0.3
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
            String[] fieldNames = {};
            if (classDriven && fieldMappingAnnotation != null) fieldNames = fieldMappingAnnotation.value();
            if (fieldNames.length == 0) fieldNames = new String[] { field.getName() };

            LINQ.stream(fieldNames).forEach(f -> allFieldsMap.put(f, field));

            Represents.SourceTargetField targetFieldAnnotation = field.getAnnotation(Represents.SourceTargetField.class);
            if (targetFieldAnnotation == null) continue;

            if (annotationTargetField != null)
                throw new IllegalStateException("Representation class cannot have more than one SourceTargetField annotations.");
            annotationTargetField = field;
            field.setAccessible(true);
        }

        for (Method method : annotationClass.getDeclaredMethods()) {
            Represents.FieldMapping fieldMappingAnnotation = method.getAnnotation(Represents.FieldMapping.class);
            String[] fieldMappingName = {};
            if (fieldMappingAnnotation != null && !classDriven) {
                if (fieldMappingAnnotation.excluded()) continue;
                fieldMappingName = fieldMappingAnnotation.value();
            }

            LINQ.stream(fieldMappingName).forEach(f -> {
                Field mappedField = allFieldsMap.get(f);
                if (mappedField == null) return;
                mappedField.setAccessible(true);

                mappedAnnotationFields.add(new Tuple<>(method, mappedField));
            });
        }
    }

    /**
     * Attempts to create a representation for the provided annotation based on all registered classes via the {@link Represents} annotation.
     * This method is an unsafe shortcut to the {@link Representer#get(Annotation, Class)} method as it assumes the provided annotation has only
     * one matching representation which conforms to {@code T}. Unless absolutely certain it is preferred to utilize the {@link Representer#get(Annotation, Class)}
     * method which appropriately handles type safety and will always match the correct return class.
     *
     * @param annotation The annotation to create a representation for
     * @return A new representation for the given annotation
     * @param <T> The expected type of the returned representation
     *           
     * @see #get(Annotation, Class) 
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Annotation annotation) {
        Class<? extends Annotation> annotationClass = annotation.getClass();
        Representer representation = ANNOTATION_REPRESENTATION_MAP.get(annotationClass);
        if (representation != null) return representation.createRepresentation(annotation);

        Represents representAnnotation = annotation.getClass().getAnnotation(Represents.class);
        if (representAnnotation == null) throw new AnnotationConfigurationException("Cannot create a representation for annotation %s. Annotation is not marked with @Represents!".formatted(annotation));
        Class<?>[] annotationToClassRepresentations = representAnnotation.value();
        if (annotationToClassRepresentations.length > 1) throw new AnnotationConfigurationException("@Represents declared for annotations cannot have more than one representation class.");

        return (T) getAndRegisterRepresentation(annotation, annotationClass, annotationToClassRepresentations[0]);
    }

    /**
     * Creates (and/or registers) a representation instance for a given annotation. The provided class <b>MUST</b> have a valid
     * no-args constructor otherwise this method will fail. Technically, any class can be used as a representation class, but
     * for optimal compatability, either the {@code annotation} or {@code representationClass} should have a {@link Represents} annotation in it.
     *
     * @param annotation The annotation to create a representation for.
     * @param representationClass The class used to create a representation for the given annotation.
     * @return A new instance of {@code representationClass} modelling the given annotation
     * @param <T> The type of the returned representation
     *           
     * @see #get(Annotation) 
     */
    public static <T> T get(Annotation annotation, Class<T> representationClass) {
        return getAndRegisterRepresentation(annotation, annotation.getClass(), representationClass);
    }

    private static <T> T getAndRegisterRepresentation(Annotation annotation, Class<? extends Annotation> annotationClass, Class<T> representationClass) {
        Represents representationClassAnnotation = representationClass.getAnnotation(Represents.class);
        if (representationClassAnnotation != null) {
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

    private <T> T createRepresentation(Annotation annotation) {
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
