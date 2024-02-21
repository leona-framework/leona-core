package com.sylvona.leona.core.aop.annotations;

import com.sylvona.leona.core.aop.exceptional.Exceptional;
import com.sylvona.leona.core.aop.exceptional.ExceptionalAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables the usage of {@link Exceptionally} throughout Spring applications. This is most commonly
 * used as follows:
 * <pre class="code">
 * &#064;Configuration
 * &#064;EnableExceptionally
 * public class AppConfig {
 *     // Class content
 * }</pre>
 *
 * When present, enhances methods annotated with the {@link Exceptionally} annotation to produce
 * an {@link Exceptional} return result based on the result
 * of the annotated method.
 *
 * @author Evan Cowin
 * @see Exceptionally
 * @since 0.0.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ExceptionalAutoConfiguration.class)
public @interface EnableExceptionally {
}
