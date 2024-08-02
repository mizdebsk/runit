package io.kojan.runit.api;

/**
 * When put on RPM file test template method annotated with {@link FileTest} it
 * makes test template include files with names matching specified regular
 * expression.
 */
public @interface IncludeFileName {
    /**
     * Specifies regular expression that is matched against file names.
     * 
     * @return regular expression
     */
    String value();
}
