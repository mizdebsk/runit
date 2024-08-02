package io.kojan.runit.api;

/**
 * When put on RPM package test template method annotated with
 * {@link PackageTest} or {@link FileTest} it makes test template include source
 * packages with names matching specified regular expression, or all source
 * packages if regular expression is not specified.
 */
public @interface IncludeSource {
    /**
     * Optional regular expression that is matched against package names. Can be
     * empty, in which case all package names are matched.
     * 
     * @return regular expression
     */
    String value() default "";
}
