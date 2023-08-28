package com.sylvona.leona.core.commons;

/**
 * A singleton class that represents a void-like value. This class provides a way to express the concept of
 * void in scenarios where a type is mandated, but no substantial value is necessary.
 * <p>
 * This class is designed to be utilized in cases where a non-null return value is demanded, yet it signifies a
 * non-operational or non-functional outcome. An exemplary usage of this class is within streaming frameworks like
 * Reactor's Mono, where a distinct but non-meaningful return type is required.
 * <p>
 * The singleton instance of {@code VoidLike} can be accessed through the {@link #INSTANCE} field.
 */
public final class VoidLike {

    /**
     * The singleton instance of {@code VoidLike}.
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final VoidLike INSTANCE = new VoidLike();

    /**
     * Private constructor to prevent external instantiation of {@code VoidLike}.
     */
    private VoidLike() {
    }
}

