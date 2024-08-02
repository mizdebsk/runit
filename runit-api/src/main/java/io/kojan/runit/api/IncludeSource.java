package io.kojan.runit.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When put on RPM package test template method annotated with
 * {@link PackageTest} or {@link FileTest} it makes test template include source
 * packages with names matching specified regular expression, or all source
 * packages if regular expression is not specified.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IncludeSource {
    /**
     * Optional regular expression that is matched against package names. Can be
     * empty, in which case all package names are matched.
     * 
     * @return regular expression
     */
    String value() default "";
}
