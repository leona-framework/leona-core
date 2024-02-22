package com.sylvona.leona.core.commons.naming;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ScreamingKebabCaseNamingConvention implements SegmentedNamingConvention {
    public static final ScreamingKebabCaseNamingConvention INSTANCE = new ScreamingKebabCaseNamingConvention();

    @Override
    public String format(String input, @Nullable NamingConvention convention) {
        if (convention == null)
            convention = NamingConventionUtils.getNamingConvention(input);

        if (convention instanceof ScreamingKebabCaseNamingConvention) {
            return input;
        }

        if (convention instanceof SegmentedNamingConvention segmentedNamingConvention) {
            List<String> stringSegments = new ArrayList<>();
            for (String segment : segmentedNamingConvention.getSegments(input)) {
                stringSegments.add(StringUtils.upperCase(segment));
            }
            return String.join("-", stringSegments);
        }

        return StringUtils.upperCase(input).replace(" ", "-");
    }

    @Override
    public boolean followsConvention(String input) {
        boolean containsDash = false;
        for (char ch : input.toCharArray()) {
            if (ch == '-') containsDash = true;
            else if (!Character.isUpperCase(ch)) return false;
        }
        return containsDash;
    }

    @Override
    public String[] getSegments(@NotNull String input) {
        return StringUtils.strip(input, "_").split("-");
    }
}
