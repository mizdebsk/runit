package io.kojan.runit.api;

import java.util.stream.Stream;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.javadeptools.rpm.RpmPackage;

/**
 * Context in which package tests annotated with {@link PackageTest} are ran.
 * <p>
 * Specifies the particular RPM package on which package tests are ran.
 */
public interface PackageContext extends GlobalContext {
    /**
     * Get the particular RPM package on which package test is ran.
     * 
     * @return RPM package
     */
    RpmPackage getRpmPackage();

    /**
     * Get the particular RPM package on which package test is ran.
     * 
     * @return RPM package info
     */
    default RpmInfo getRpmInfo() {
        return getRpmPackage().getInfo();
    }

    /**
     * Produces a {@link Stream} of {@link FileContext}s that allows iterating over
     * all files in the package context.
     * 
     * @return stream of file contexts
     */
    Stream<FileContext> getFileSubcontexts(boolean withContent);
}
