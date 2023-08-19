package com.tealeaf.leona.core.utils;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;

public class SpringBridgeUtils {
    /**
     * Determines the most appropriate autowired constructor for the given class using the provided ApplicationContext.
     *
     * @param cls     The class for which to determine the constructor.
     * @param context The ApplicationContext used for bean resolution.
     * @return The selected Constructor to be used for autowiring or null if no such constructor could be determined
     */
    public static Constructor<?> determineAutowiredConstructor(Class<?> cls, ApplicationContext context) {
        Constructor<?>[] constructors = cls.getDeclaredConstructors();

        if (constructors.length == 0) return null;
        if (constructors.length == 1) return constructors[0];

        Comparator<Constructor<?>> ctorAvailableBeanComparator = Comparator.comparingInt(ctor -> {
            int availableBeans = 0;
            Class<?>[] parameters = ctor.getParameterTypes();
            for (Class<?> parameter : parameters) {
                if (!context.getBeansOfType(parameter).isEmpty()) availableBeans++;
            }
            return availableBeans;
        });

        Comparator<Constructor<?>> argCountComparator = Comparator.comparingInt(Constructor::getParameterCount);


        final PriorityQueue<Constructor<?>> mostArgConstructorWithSuitableBeans = new PriorityQueue<>(ctorAvailableBeanComparator);
        final PriorityQueue<Constructor<?>> mostArgConstructors = new PriorityQueue<>(argCountComparator);

        boolean autowiredAnnotationPresent = false;

        for (Constructor<?> ctor : constructors) {
            if (AnnotationHelper.hasNestedAnnotation(ctor, Primary.class)) return ctor;
            boolean addToQueue = false;

            if (AnnotationHelper.hasNestedAnnotation(ctor, Autowired.class)) {
                if (!autowiredAnnotationPresent) {
                    // Clear the previous constructors, which were not annotated with @Autowired
                    mostArgConstructors.clear();
                    mostArgConstructorWithSuitableBeans.clear();
                    autowiredAnnotationPresent = true;
                }
                addToQueue = true;
            } else if (!autowiredAnnotationPresent) {
                addToQueue = true;
            }

            if (addToQueue) {
                mostArgConstructorWithSuitableBeans.add(ctor);
                mostArgConstructors.add(ctor);
            }
        }

        if (!mostArgConstructorWithSuitableBeans.isEmpty()) {
            return mostArgConstructorWithSuitableBeans.remove();
        }

        return mostArgConstructors.isEmpty() ? null : mostArgConstructors.remove();
    }


    /**
     * Resolves arguments for an autowired constructor based on the provided constructor and application context.
     *
     * @param ctor    The constructor for which to resolve arguments.
     * @param context The ApplicationContext used for bean and property resolution.
     * @return An array of resolved constructor arguments.
     */
    public static Object[] resolveAutowiredConstructorArguments(Constructor<?> ctor, ApplicationContext context) {
        Environment environment = context.getEnvironment();
        Annotation[][] parameterAnnotations = ctor.getParameterAnnotations();
        Class<?>[] parameterTypes = ctor.getParameterTypes();

        Object[] args = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            Annotation[] paramAnnotations = parameterAnnotations[i];
            Class<?> parameterType = parameterTypes[i];

            Value value = AnnotationHelper.getNestedAnnotation(paramAnnotations, Value.class);
            if (value != null) {
                args[i] = environment.getProperty(value.value(), parameterType);
            }

            Qualifier qualifier = AnnotationHelper.getNestedAnnotation(paramAnnotations, Qualifier.class);
            if (qualifier == null || qualifier.value().isEmpty()) {
                args[i] = context.getBean(parameterType);
            } else {
                args[i] = context.getBean(qualifier.value(), parameterType);
            }
        }

        return args;
    }
}


