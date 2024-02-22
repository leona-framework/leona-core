package com.sylvona.leona.core.utils;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class BeanFirstClassConstructor {
    private final ApplicationContext applicationContext;

    public BeanFirstClassConstructor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    public <T> T getOrCreate(Class<T> cls) {
        ObjectProvider<T> beanProvider = applicationContext.getBeanProvider(cls);
        T instance = beanProvider.getIfAvailable();
        if (instance != null) return instance;

        Constructor<T> constructor;
        try {
            constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Could not find empty constructor for class %s".formatted(cls), e);
        }

        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Could not constructor instance for class %s".formatted(cls), e);
        }
    }
}
