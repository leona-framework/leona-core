package com.sylvona.leona.core.commons.naming;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TrainCaseNamingConvention implements SegmentedNamingConvention {
    public static final TrainCaseNamingConvention INSTANCE = new TrainCaseNamingConvention();

    @Override
    public String format(String input, @Nullable NamingConvention convention) {
        if (convention == null)
            convention = NamingConventionUtils.getNamingConvention(input);

        if (convention instanceof TrainCaseNamingConvention) {
            return input;
        }

        if (convention instanceof SegmentedNamingConvention segmentedNamingConvention) {
            List<String> stringSegments = new ArrayList<>();
            for (String segment : segmentedNamingConvention.getSegments(input)) {
                stringSegments.add(Character.toUpperCase(segment.charAt(0)) + segment.substring(1).toLowerCase());
            }
            return String.join("-", stringSegments);
        }

        return StringUtils.capitalize(input).replace(" ", "-");
    }

    @Override
    public boolean followsConvention(String input) {
        if (input.isEmpty()) return false;
        if (!Character.isUpperCase(input.charAt(0))) return false;

        boolean containsDash = false;
        boolean lastWasDash = false;
        int lastUpperCount = 1;

        char[] charArray = input.toCharArray();

        for (int i = 1; i < charArray.length; i++) {
            char ch = charArray[i];
            if (ch == ' ') return false;
            if (Character.isUpperCase(ch)) {
                if (!lastWasDash) {
                    if (lastUpperCount == 0) return false;
                }
                lastUpperCount++;
            }
            else {
                lastUpperCount = 0;
            }
            if (ch == '-') {
                containsDash = lastWasDash = true;
            } else {
                lastWasDash = false;
            }
        }

        return containsDash;
    }

    @Override
    public String[] getSegments(@NotNull String input) {
        int strLen = input.length();
        if (strLen == 0) return new String[0];
        if (strLen <= 2) return new String[] { input };

        List<String> segmentList = new ArrayList<>();
        StringBuilder segmentBuilder = new StringBuilder(Math.min(strLen, 16));

        int index = 0;

        char ch = input.charAt(index++);
        while (ch == '-' && index < strLen) {
            ch = input.charAt(index++);
        }
        index--;

        while (index < strLen) {
            ch = input.charAt(index++);
            if (ch != '-') {
                segmentBuilder.append(ch);
            } else {
                segmentList.add(segmentBuilder.toString());
                segmentBuilder = new StringBuilder(Math.min(strLen - index, 16));
            }
        }

        if (!segmentBuilder.isEmpty()) {
            segmentList.add(segmentBuilder.toString());
        }

        return segmentList.toArray(String[]::new);
    }
}
