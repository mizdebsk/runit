package io.kojan.runit.api;

/**
 * When put on RPM package test template method annotated with
 * {@link PackageTest} it makes test template include binary packages with names
 * matching specified regular expression.
 */
public @interface IncludeBinaryName {
    /**
     * Specifies regular expression that is matched against package names.
     * 
     * @return regular expression
     */
    String value();
}
