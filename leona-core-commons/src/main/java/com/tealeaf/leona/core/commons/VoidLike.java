package org.lyora.leona.core.commons;

public class VoidLike {
    public static final VoidLike INSTANCE = new VoidLike();

    private VoidLike() {
    }

    public VoidLike newInstance() {
        return new VoidLike();
    }
}
