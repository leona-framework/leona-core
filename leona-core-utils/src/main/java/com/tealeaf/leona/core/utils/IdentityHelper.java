package com.tealeaf.leona.core.utils;

import jakarta.annotation.Nullable;

public class IdentityHelper {
    public static boolean isClass(@Nullable Object object, Class<?> cls) {
        return object != null && object.getClass().equals(cls);
    }

    public static boolean isClassOrSubclass(@Nullable Object object, Class<?> cls) {
        return object != null && cls.isAssignableFrom(object.getClass());
    }

    public static boolean isClassOrSuperclass(@Nullable Object object, Class<?> cls) {
        return object != null && object.getClass().isAssignableFrom(cls);
    }
}
