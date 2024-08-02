package io.kojan.runit.api;

/**
 * When put on RPM file test template method annotated with {@link FileTest} it
 * makes test template exclude files with names matching specified regular
 * expression.
 */
public @interface ExcludeFileName {
    /**
     * Specifies regular expression that is matched against file names.
     * 
     * @return regular expression
     */
    String value();
}
