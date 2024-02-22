package com.sylvona.leona.core.commons.naming;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class KebabCaseNamingConvention implements SegmentedNamingConvention {
    public static final KebabCaseNamingConvention INSTANCE = new KebabCaseNamingConvention();

    @Override
    public String format(String input, @Nullable NamingConvention convention) {
        if (convention == null)
            convention = NamingConventionUtils.getNamingConvention(input);

        if (convention instanceof KebabCaseNamingConvention) {
            return input;
        }

        if (convention instanceof SegmentedNamingConvention segmentedNamingConvention) {
            List<String> stringSegments = new ArrayList<>();
            for (String segment : segmentedNamingConvention.getSegments(input)) {
                stringSegments.add(StringUtils.lowerCase(segment));
            }
            return String.join("-", stringSegments);
        }

        // If the convention for given input is a non-segmented convention the best we can do is lowercase the whole thing and replace spaces with underscores
        return StringUtils.lowerCase(input).replace(" ", "-");
    }

    @Override
    public boolean followsConvention(String input) {
        boolean containsDash = false;
        for (char ch : input.toCharArray()) {
            if (ch == '-') containsDash = true;
            else if (ch == ' ' || Character.isUpperCase(ch)) return false;
        }
        return containsDash;
    }

    @Override
    public String[] getSegments(@NotNull String input) {
        return input.split("-");
    }
}
