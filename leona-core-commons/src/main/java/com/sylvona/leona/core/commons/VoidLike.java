package com.sylvona.leona.core.commons;

/**
 * A class representing a {@code void} value.
 *
 * @author Evan Cowin
 * @since 0.0.1
 */
public class VoidLike {
    /**
     * The static instance of {@link VoidLike}.
     */
    public static final VoidLike INSTANCE = new VoidLike();

    private VoidLike() {
    }

    /**
     * Creates a new instance of {@link VoidLike}.
     * @return A new instance of {@link VoidLike}.
     */
    public VoidLike newInstance() {
        return new VoidLike();
    }
}
