package com.sylvona.leona.core.commons.naming;

import com.sylvona.leona.core.commons.streams.LINQ;

import java.util.LinkedHashSet;
import java.util.List;

public final class NamingConventionDatabase {
    private static NamingConventionDatabase instance;

    private final LinkedHashSet<NamingConvention> namingConventions;

    NamingConventionDatabase(NamingConvention... builtinConventions) {
        instance = this;
        namingConventions = LINQ.stream(builtinConventions).collect(LinkedHashSet::new);
    }

    public static NamingConventionDatabase instance() {
        return instance;
    }

    public void addNamingConvention(NamingConvention convention) {
        namingConventions.add(convention);
    }

    public List<NamingConvention> getAllNamingConventions() {
        return namingConventions.stream().toList();
    }
}
