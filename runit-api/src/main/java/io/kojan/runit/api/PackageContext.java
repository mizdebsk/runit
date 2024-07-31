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
     * <p>
     * For performance reasons, there is a possibility to stream through files from
     * RPM header only, without reading CPIO archive.
     * 
     * @param withContent whether RPM archive contents should be read; if
     *                    {@code true} then CPIO payload of RPM package is
     *                    uncompressed and all file contents are read into memory,
     *                    if {@code false} then only RPM header is processed and
     *                    file contents are not available
     * 
     * @return stream of file contexts
     */
    Stream<FileContext> getFileSubcontexts(boolean withContent);
}
