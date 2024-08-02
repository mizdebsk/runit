package io.kojan.runit.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container annotation to hold any number of {@link ExcludeBinary} annotations,
 * zero or more.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeBinaries {
    /**
     * Return annotations held by the container.
     * 
     * @return annotations that this container holds
     */
    ExcludeBinary[] value();
}
