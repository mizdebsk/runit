package io.kojan.runit.api;

/**
 * When put on RPM package test template method annotated with
 * {@link PackageTest} it makes test template exclude non-source (binary)
 * packages.
 */
public @interface ExcludeBinaryRPM {
}
