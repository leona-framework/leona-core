package com.sylvona.leona.core.functional;

import com.sylvona.leona.core.functional.exceptional.ExceptionalAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableAspectJAutoProxy
@Import(ExceptionalAutoConfiguration.class)
public class LeonaFunctionalAopAutoconfiguration {
}
