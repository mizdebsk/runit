package io.kojan.runit.api;

/**
 * When put on RPM package test template method annotated with
 * {@link PackageTest} or {@link FileTest} it makes test template include source
 * and binary packages with source names matching specified regular expression.
 */
public @interface IncludeSourceName {
    /**
     * Specifies regular expression that is matched against package source names.
     * 
     * @return regular expression
     */
    String value();
}
