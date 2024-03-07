package com.sylvona.leona.core.aop.annotations;

import com.sylvona.leona.core.aop.exceptional.Exceptional;
import com.sylvona.leona.core.aop.exceptional.ExceptionalAutoConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

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
    /**
     * Indicate whether subclass-based (CGLIB) proxies are to be created as opposed
     * to standard Java interface-based proxies. The default is {@code false}. <strong>
     * Applicable only if {@link #mode()} is set to {@link AdviceMode#PROXY}</strong>.
     * <p>Note that setting this attribute to {@code true} will affect <em>all</em>
     * Spring-managed beans requiring proxying, not just those marked with {@code @Cacheable}.
     * For example, other beans marked with Spring's {@code @Transactional} annotation will
     * be upgraded to subclass proxying at the same time. This approach has no negative
     * impact in practice unless one is explicitly expecting one type of proxy vs another,
     * e.g. in tests.
     */
    boolean proxyTargetClass() default false;

    /**
     * Indicate how caching advice should be applied.
     * <p><b>The default is {@link AdviceMode#PROXY}.</b>
     * Please note that proxy mode allows for interception of calls through the proxy
     * only. Local calls within the same class cannot get intercepted that way;
     * a caching annotation on such a method within a local call will be ignored
     * since Spring's interceptor does not even kick in for such a runtime scenario.
     * For a more advanced mode of interception, consider switching this to
     * {@link AdviceMode#ASPECTJ}.
     */
    AdviceMode mode() default AdviceMode.PROXY;

    /**
     * Indicate the ordering of the execution of the caching advisor
     * when multiple advices are applied at a specific joinpoint.
     * <p>The default is {@link Ordered#LOWEST_PRECEDENCE}.
     */
    int order() default Ordered.LOWEST_PRECEDENCE;
}
