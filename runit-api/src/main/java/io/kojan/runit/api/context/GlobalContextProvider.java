/*-
 * Copyright (c) 2024 Red Hat, Inc.
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
package io.kojan.runit.api.context;

import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.GlobalContext;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A static class for providing {@link GlobalContext} for test execution.
 *
 * <p>Context can be either obtained by reading RPM packages from file system locations discovered
 * through environment variables, or set by some other code via one of static setter methods.
 *
 * <p>When context is not set by other means, then RPM files under directory pointed to by
 * environment variable {@code RUNIT_TEST_ARTIFACTS} are used as context. In case that variable is
 * not set, {@code TEST_ARTIFACTS} is used as fallback, and if that variable is not set either,
 * directory {@code /tmp/test-artifacts} is used, if exists.
 *
 * @author Mikolaj Izdebski
 */
public class GlobalContextProvider {
    private static GlobalContext context;
    private static List<RpmPackage> rpmPackages;
    private static Path artifactsDir;

    private GlobalContextProvider() {}

    /**
     * Obtain an instance of {@link GlobalContext}.
     *
     * @return global context with all RPM packages under test
     */
    public static GlobalContext getContext() {
        if (context == null) {
            context = new GlobalContextImpl(getRpmPackages());
        }
        return context;
    }

    /**
     * Save {@link GlobalContext} for tests to use.
     *
     * @param context global context to save
     */
    public static void setContext(GlobalContext context) {
        GlobalContextProvider.context = context;
    }

    /**
     * Make a {@link GlobalContext} out of specified list of packages and save it for tests to use.
     *
     * @param rpmPackages list of RPM packages that constitute context
     */
    public static void setRpmPackages(List<RpmPackage> rpmPackages) {
        GlobalContextProvider.rpmPackages = rpmPackages;
    }

    /**
     * Make a {@link GlobalContext} out of RPM packages contained in specified directory and save it
     * for tests to use.
     *
     * @param artifactsDir path to directory containing RPM packages that constitute context
     */
    public static void setArtifactsDir(Path artifactsDir) {
        GlobalContextProvider.artifactsDir = artifactsDir;
    }

    private static String getArtifactsDir() {
        String dir = System.getenv("RUNIT_TEST_ARTIFACTS");
        if (dir != null) {
            System.err.println("RUnit: Reading artifacts from RUNIT_TEST_ARTIFACTS (" + dir + ")");
            return dir;
        }
        dir = System.getenv("TEST_ARTIFACTS");
        if (dir != null) {
            System.err.println("RUnit: RUNIT_TEST_ARTIFACTS environment variable was not set.");
            System.err.println(
                    "RUnit: Reading artifacts from fallback location TEST_ARTIFACTS (" + dir + ")");
            return dir;
        }
        dir = "/tmp/test-artifacts";
        System.err.println(
                "RUnit: Neither RUNIT_TEST_ARTIFACTS nor TEST_ARTIFACTS environment variable was set.");
        System.err.println("RUnit: Reading artifacts from fallback location " + dir);
        return dir;
    }

    private static List<RpmPackage> getRpmPackages() {
        if (rpmPackages != null) {
            return rpmPackages;
        }
        try {
            if (artifactsDir == null) {
                artifactsDir = Path.of(getArtifactsDir());
            }
            if (!Files.isDirectory(artifactsDir)) {
                throw new RuntimeException("Not a directory: " + artifactsDir);
            }
            List<Path> rpmPaths =
                    Files.walk(artifactsDir, FileVisitOption.FOLLOW_LINKS) //
                            .filter(Files::isRegularFile) //
                            .filter(p -> p.getFileName().toString().endsWith(".rpm")) //
                            .collect(Collectors.toList());
            if (rpmPaths.isEmpty()) {
                throw new RuntimeException(
                        "Directory does not contain any RPM packages: " + artifactsDir);
            }
            rpmPackages = new ArrayList<>();
            for (Path path : rpmPaths) {
                RpmPackage rpmPackage = new RpmPackage(path);
                rpmPackages.add(rpmPackage);
            }
            return rpmPackages;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
