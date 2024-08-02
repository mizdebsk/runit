package io.kojan.runit.api;

/**
 * When put on RPM package test template method annotated with
 * {@link PackageTest} or {@link FileTest} it makes test template exclude binary
 * packages with names matching specified regular expression.
 */
public @interface ExcludeBinaryName {
    /**
     * Specifies regular expression that is matched against package names.
     * 
     * @return regular expression
     */
    String value();
}
