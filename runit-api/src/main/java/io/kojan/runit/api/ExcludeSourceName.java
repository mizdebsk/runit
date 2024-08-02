package io.kojan.runit.api;

/**
 * When put on RPM package test template method annotated with
 * {@link PackageTest} it makes test template exclude source and binary packages
 * with source names matching specified regular expression.
 */
public @interface ExcludeSourceName {
    /**
     * Specifies regular expression that is matched against package source names.
     * 
     * @return regular expression
     */
    String value();
}
