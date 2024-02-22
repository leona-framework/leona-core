package com.sylvona.leona.core.commons.naming;

import jakarta.annotation.Nullable;

public class NoNamingConvention implements NamingConvention {
    public static final NoNamingConvention INSTANCE = new NoNamingConvention();

    @Override
    public String format(String input, @Nullable NamingConvention convention) {
        return input;
    }

    @Override
    public boolean followsConvention(String input) {
        return true;
    }
}
