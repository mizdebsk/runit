/*-
 * Copyright (c) 2024-2025 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.kojan.runit.api;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.javadeptools.rpm.RpmPackage;
import java.util.stream.Stream;

/**
 * Context in which package tests annotated with {@link PackageTest} are ran.
 *
 * <p>Specifies the particular RPM package on which package tests are ran.
 *
 * @author Mikolaj Izdebski
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
     * Produces a {@link Stream} of {@link FileContext}s that allows iterating over all files in the
     * package context.
     *
     * <p>For performance reasons, there is a possibility to stream through files from RPM header
     * only, without reading CPIO archive.
     *
     * @param withContent whether RPM archive contents should be read; if {@code true} then CPIO
     *     payload of RPM package is uncompressed and all file contents are read into memory, if
     *     {@code false} then only RPM header is processed and file contents are not available
     * @return stream of file contexts
     */
    Stream<FileContext> getFileSubcontexts(boolean withContent);
}
