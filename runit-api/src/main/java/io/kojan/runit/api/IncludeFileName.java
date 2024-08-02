package io.kojan.runit.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When put on RPM file test template method annotated with {@link FileTest} it
 * makes test template include files with names matching specified regular
 * expression.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IncludeFileNames.class)
public @interface IncludeFileName {
    /**
     * Specifies regular expression that is matched against file names.
     * 
     * @return regular expression
     */
    String value();
}
