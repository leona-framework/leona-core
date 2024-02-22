package com.sylvona.leona.core.commons.naming;

import jakarta.validation.constraints.NotNull;

public interface SegmentedNamingConvention extends NamingConvention {
    String[] getSegments(@NotNull String input);
}
