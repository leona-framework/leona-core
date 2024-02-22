package com.sylvona.leona.core.commons.naming;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CamelCaseNamingConvention implements SegmentedNamingConvention {
    public static final CamelCaseNamingConvention INSTANCE = new CamelCaseNamingConvention();

    @Override
    public String format(String input, @Nullable NamingConvention convention) {
        if (input.isEmpty()) return input;
        if (convention == null)
            convention = NamingConventionUtils.getNamingConvention(input);

        if (convention instanceof CamelCaseNamingConvention) {
            return input;
        }

        if (convention instanceof SegmentedNamingConvention segmentedNamingConvention) {
            StringBuilder output = new StringBuilder();
            String[] segments = segmentedNamingConvention.getSegments(input);
            if (segments.length == 0) return input;
            output.append(segments[0].toLowerCase());

            for (int i = 1; i < segments.length; i++) {
                String segment = segments[i];
                output.append(segment.substring(0, 1).toUpperCase()).append(segment.substring(1).toLowerCase());
            }

            return output.toString();
        }

        // If the convention for given input is a non-segmented convention the best we can do is capitalize the first letter
        return StringUtils.uncapitalize(input);
    }

    @Override
    public boolean followsConvention(String input) {
        int strLen = input.length();
        if (strLen == 0) return false;
        if (Character.isUpperCase(input.charAt(0))) return false;

        for (int i = 1; i < strLen; i++) {
            char ch = input.charAt(i);
            if (ch == '_' || ch == '-' || ch == ' ') return false;
        }

        return true;
    }

    @Override
    public String[] getSegments(@NotNull String input) {
        if (input.isEmpty()) return new String[0];
        int strLen = input.length();
        if (strLen <= 2) return new String[] { input };

        List<String> segmentList = new ArrayList<>();
        StringBuilder segmentBuilder = new StringBuilder(Math.min(strLen, 16));

        int index = 0;
        int lastUpperCount = 0;
        while (index < strLen) {
            char ch = input.charAt(index);

            if (Character.isUpperCase(ch)) {
                // If the last character was upper case (ex: an acronym such as AWS), append to builder
                if (lastUpperCount++ > 0) {
                    segmentBuilder.append(ch);
                }
                // Otherwise add segment to segment list
                else {
                    segmentList.add(segmentBuilder.toString());
                    segmentBuilder = new StringBuilder(Math.min(strLen - index, 16));
                    segmentBuilder.append(ch);
                }
            } else {
                if (lastUpperCount > 1) {
                    segmentList.add(segmentBuilder.toString());
                    segmentBuilder = new StringBuilder(Math.min(strLen - index, 16));
                }

                segmentBuilder.append(ch);
                lastUpperCount = 0;
            }

            if (++index >= strLen) {
                segmentList.add(segmentBuilder.toString());
            }
        }

        return segmentList.toArray(String[]::new);
    }
}
