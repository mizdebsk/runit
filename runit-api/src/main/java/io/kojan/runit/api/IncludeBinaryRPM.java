package io.kojan.runit.api;

/**
 * When put on RPM package test template method annotated with
 * {@link PackageTest} or {@link FileTest} it makes test template include
 * non-source (binary) packages.
 */
public @interface IncludeBinaryRPM {
}
