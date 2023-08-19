package com.sylvona.leona.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class AnnotationHelper {
    public static boolean hasAnnotation(Object object, Class<? extends Annotation> annotation) {
        return object != null && object.getClass().getAnnotation(annotation) != null;
    }

    public static boolean hasAnnotation(Constructor<?> ctor, Class<? extends Annotation> annotation) {
        return ctor != null && ctor.getAnnotation(annotation) != null;
    }

    public static boolean hasNestedAnnotation(Object object, Class<? extends Annotation> annotation) {
        return getNestedAnnotation(object, annotation) != null;
    }

    public static boolean hasNestedAnnotation(Constructor<?> ctor, Class<? extends Annotation> annotation) {
        return getNestedAnnotation(ctor.getAnnotations(), annotation) != null;
    }

    public static <T extends Annotation> T getAnnotation(Object object, Class<T> annotation) {
        return object == null ? null : object.getClass().getAnnotation(annotation);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T getNestedAnnotation(Annotation[] sourceAnnotations, Class<T> annotation) {
        Stack<Annotation> annotationStack = Arrays.stream(sourceAnnotations).collect(Collectors.toCollection(Stack::new));
        Set<Annotation> checkedAnnotations = new HashSet<>();
        while (!annotationStack.isEmpty()) {
            Annotation checked = annotationStack.pop();
            if (!checkedAnnotations.add(checked)) continue;

            if (annotation.isInstance(checked)) return (T) checked;

            Annotation[] nestedAnnotations = checked.annotationType().getAnnotations();
            for (Annotation nested : nestedAnnotations) {
                annotationStack.push(nested);
            }
        }
        return null;
    }

    public static <T extends Annotation> T getNestedAnnotation(Annotation sourceAnnotation, Class<T> annotation) {
        Annotation[] annotations = sourceAnnotation.annotationType().getAnnotations();
        Annotation[] annotationPlusThis = new Annotation[annotations.length + 1];
        System.arraycopy(annotations, 0, annotationPlusThis, 1, annotations.length);
        annotationPlusThis[0] = sourceAnnotation;
        return getNestedAnnotation(annotationPlusThis, annotation);
    }

    public static <T extends Annotation> T getNestedAnnotation(Object object, Class<T> annotation) {
        return object == null ? null : getNestedAnnotation(object.getClass().getAnnotations(), annotation);
    }
}
