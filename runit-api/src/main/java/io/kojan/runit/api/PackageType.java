package io.kojan.runit.api;

/**
 * Represents the kind of RPM package &ndash; source RPM (SRPM) or binary RPM.
 */
public enum PackageType {
    /** Represents a source RPM package (SRPM). */
    SOURCE,
    /** Represents a binary RPM package. */
    BINARY,
    /** Represents both source and binary RPM packages. */
    BOTH;
}
