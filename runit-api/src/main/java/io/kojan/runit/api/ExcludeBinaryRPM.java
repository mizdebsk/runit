package io.kojan.runit.api;

/**
 * When put on RPM package test template method annotated with
 * {@link PackageTest} or {@link FileTest} it makes test template exclude
 * non-source (binary) packages.
 */
public @interface ExcludeBinaryRPM {
}
