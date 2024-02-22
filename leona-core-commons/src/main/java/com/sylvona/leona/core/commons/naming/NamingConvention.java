package com.sylvona.leona.core.commons.naming;

import jakarta.annotation.Nullable;

public interface NamingConvention {
    default String format(String input) {
        return format(input, (NamingConvention) null);
    }

    default String format(String input, @Nullable Class<? extends NamingConvention> convention) {
        return format(input, NamingConventionUtils.fromClass(convention));
    }

    String format(String input, @Nullable NamingConvention convention);

    boolean followsConvention(String input);
}
