package com.sylvona.leona.core.commons.naming;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PascalCaseNamingConvention implements SegmentedNamingConvention {
    public static final PascalCaseNamingConvention INSTANCE = new PascalCaseNamingConvention();

    @Override
    public String format(String input, @Nullable NamingConvention convention) {
        if (convention == null) convention = NamingConventionUtils.getNamingConvention(input);

        if (convention instanceof PascalCaseNamingConvention) {
            return input;
        }

        if (convention instanceof SegmentedNamingConvention segmentedNamingConvention) {
            StringBuilder output = new StringBuilder();
            for (String segment : segmentedNamingConvention.getSegments(input)) {
                output.append(Character.toUpperCase(segment.charAt(0))).append(segment.substring(1).toLowerCase());
            }
            return output.toString();
        }

        // If the convention for given input is a non-segmented convention the best we can do is capitalize the first letter
        return StringUtils.capitalize(input);
    }

    @Override
    public boolean followsConvention(String input) {
        int strLen = input.length();
        if (strLen == 0) return false;
        if (!Character.isUpperCase(input.charAt(0))) return false;

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
        boolean lastWasUpper = true;
        while (index < strLen) {
            char ch = input.charAt(index);
            
            if (Character.isUpperCase(ch)) {
                // If the last character was upper case (ex: an acronym such as AWS), append to builder
                if (lastWasUpper) {
                    segmentBuilder.append(ch);
                }
                // Otherwise add segment to segment list
                else {
                    segmentList.add(segmentBuilder.toString());
                    segmentBuilder = new StringBuilder(Math.min(strLen - index, 16));
                    segmentBuilder.append(ch);
                }
                lastWasUpper = true;
            } else {
                segmentBuilder.append(ch);
                lastWasUpper = false;
            }

            if (++index >= strLen) {
                segmentList.add(segmentBuilder.toString());
            }
        }

        return segmentList.toArray(String[]::new);
    }


    // DISCORD_ID
    // discordId
    // DiscordId
    //
}
