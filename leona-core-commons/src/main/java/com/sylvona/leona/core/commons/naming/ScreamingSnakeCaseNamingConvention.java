package com.sylvona.leona.core.commons.naming;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ScreamingSnakeCaseNamingConvention implements SegmentedNamingConvention {
    public static final ScreamingSnakeCaseNamingConvention INSTANCE = new ScreamingSnakeCaseNamingConvention();

    @Override
    public String format(String input, @Nullable NamingConvention convention) {
        if (convention == null)
            convention = NamingConventionUtils.getNamingConvention(input);

        if (convention instanceof ScreamingSnakeCaseNamingConvention) {
            return input;
        }

        if (convention instanceof SegmentedNamingConvention segmentedNamingConvention) {
            List<String> stringSegments = new ArrayList<>();
            for (String segment : segmentedNamingConvention.getSegments(input)) {
                stringSegments.add(StringUtils.upperCase(segment));
            }
            return String.join("_", stringSegments);
        }

        // If the convention for given input is a non-segmented convention the best we can do is lowercase the whole thing and replace spaces with underscores
        return StringUtils.upperCase(input).replace(" ", "_");
    }

    @Override
    public boolean followsConvention(String input) {
        boolean containsUnderscore = false;
        for (char ch : input.toCharArray()) {
            if (ch == '_') containsUnderscore = true;
            else if (!Character.isUpperCase(ch)) return false;
        }
        return containsUnderscore;
    }

    @Override
    public String[] getSegments(@NotNull String input) {
        return StringUtils.strip(input, "_").split("_");
    }
}
