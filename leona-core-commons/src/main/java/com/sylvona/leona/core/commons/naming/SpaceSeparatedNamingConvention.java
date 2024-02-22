package com.sylvona.leona.core.commons.naming;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public class SpaceSeparatedNamingConvention implements SegmentedNamingConvention {
    public static final SpaceSeparatedNamingConvention INSTANCE = new SpaceSeparatedNamingConvention();

    @Override
    public String format(String input, @Nullable NamingConvention convention) {
        if (convention == null)
            convention = NamingConventionUtils.getNamingConvention(input);

        if (convention instanceof SpaceSeparatedNamingConvention) {
            return input;
        }

        if (convention instanceof SegmentedNamingConvention segmentedNamingConvention) {
            return String.join(" ", segmentedNamingConvention.getSegments(input));
        }

        return input;
    }

    @Override
    public boolean followsConvention(String input) {
        return input.contains(" ");
    }

    @Override
    public String[] getSegments(@NotNull String input) {
        return input.split(" ");
    }
}
