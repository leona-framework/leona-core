package com.sylvona.leona.core.commons.naming;

import com.sylvona.leona.core.commons.streams.LINQ;

public class NamingConventionUtils {
    private static final NamingConventionDatabase NAMING_CONVENTION_DATABASE = new NamingConventionDatabase(
            ScreamingSnakeCaseNamingConvention.INSTANCE,
            PascalSnakeCaseNamingConvention.INSTANCE,
            ScreamingKebabCaseNamingConvention.INSTANCE,
            TrainCaseNamingConvention.INSTANCE,
            KebabCaseNamingConvention.INSTANCE,
            SnakeCaseNamingConvention.INSTANCE,
            PascalCaseNamingConvention.INSTANCE,
            CamelCaseNamingConvention.INSTANCE,
            SpaceSeparatedNamingConvention.INSTANCE
    );

    public static NamingConvention fromClass(Class<? extends NamingConvention> conventionClass) {
        if (conventionClass == null) return null;

        if (PascalCaseNamingConvention.class.isAssignableFrom(conventionClass)) {
            return PascalCaseNamingConvention.INSTANCE;
        }

        if (SnakeCaseNamingConvention.class.isAssignableFrom(conventionClass)) {
            return SnakeCaseNamingConvention.INSTANCE;
        }

        return NoNamingConvention.INSTANCE;
    }

    public static NamingConvention getNamingConvention(String input) {
        return LINQ.stream(NAMING_CONVENTION_DATABASE.getAllNamingConventions()).firstOrDefault(nc -> nc.followsConvention(input), NoNamingConvention.INSTANCE);
    }
}
