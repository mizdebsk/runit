package io.kojan.runit.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When put on RPM file test template method annotated with {@link FileTest} it
 * makes test template exclude regular files.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeRegularFile {
}