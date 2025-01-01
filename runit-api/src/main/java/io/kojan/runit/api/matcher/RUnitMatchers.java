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
package io.kojan.runit.api.matcher;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmInfo;
import org.hamcrest.Matcher;

/**
 * A static class providing factory methods for creating Hamcrest {@link Matcher}s related to RPM
 * packages.
 *
 * @author Mikolaj Izdebski
 */
public class RUnitMatchers {
    private RUnitMatchers() {}

    /**
     * Creates a Hamcrest {@link Matcher} that matches RPM packages which source name matching
     * regular expression.
     *
     * @param regex regular expression to match against package source name
     * @return matcher for package source name
     */
    public static Matcher<RpmInfo> sourceName(String regex) {
        return new SourceNameMatcher(regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches RPM packages which name matching regular
     * expression.
     *
     * @param regex regular expression to match against package name
     * @return matcher for package name
     */
    public static Matcher<RpmInfo> name(String regex) {
        return new PackageNameMatcher(regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches source RPM packages.
     *
     * @return matcher for source RPMs
     */
    public static Matcher<RpmInfo> sourceRPM() {
        return new SourceRPMMatcher();
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches binary RPM packages.
     *
     * @return matcher for binary RPMs
     */
    public static Matcher<RpmInfo> binaryRPM() {
        return new BinaryRPMMatcher();
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches regular files.
     *
     * @return matcher for regular files
     */
    public static Matcher<RpmFile> regularFile() {
        return new RegularFileMatcher();
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches directories.
     *
     * @return matcher for directories
     */
    public static Matcher<RpmFile> directory() {
        return new DirectoryMatcher();
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches symbolic links.
     *
     * @return matcher for symbolic links
     */
    public static Matcher<RpmFile> symlink() {
        return new SymlinkMatcher();
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches files which name matching regular expression.
     *
     * @param regex regular expression to match against file names
     * @return matcher for file names
     */
    public static Matcher<RpmFile> fileName(String regex) {
        return new FileNameMatcher(regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches files which specified mode.
     *
     * @param mode file mode in the format of octal string, for example {@code "644"}
     * @return matcher for file mode
     */
    public static Matcher<RpmFile> mode(String mode) {
        return new FileModeMatcher(mode);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches packages with Provides matching specified
     * regular expression.
     *
     * @param regex regular expression to match against package Provides
     * @return matcher for package Provides
     */
    public static Matcher<RpmInfo> provides(String regex) {
        return new DependencyMatcher("Provides", RpmInfo::getProvides, regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches packages with Requires matching specified
     * regular expression.
     *
     * @param regex regular expression to match against package Requires
     * @return matcher for package Requires
     */
    public static Matcher<RpmInfo> requires(String regex) {
        return new DependencyMatcher("Requires", RpmInfo::getRequires, regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches packages with Conflicts matching specified
     * regular expression.
     *
     * @param regex regular expression to match against package Conflicts
     * @return matcher for package Conflicts
     */
    public static Matcher<RpmInfo> conflicts(String regex) {
        return new DependencyMatcher("Conflicts", RpmInfo::getConflicts, regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches packages with Obsoletes matching specified
     * regular expression.
     *
     * @param regex regular expression to match against package Obsoletes
     * @return matcher for package Obsoletes
     */
    public static Matcher<RpmInfo> obsoletes(String regex) {
        return new DependencyMatcher("Obsoletes", RpmInfo::getObsoletes, regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches packages with Recommends matching specified
     * regular expression.
     *
     * @param regex regular expression to match against package Recommends
     * @return matcher for package Recommends
     */
    public static Matcher<RpmInfo> recommends(String regex) {
        return new DependencyMatcher("Recommends", RpmInfo::getRecommends, regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches packages with Suggests matching specified
     * regular expression.
     *
     * @param regex regular expression to match against package Suggests
     * @return matcher for package Suggests
     */
    public static Matcher<RpmInfo> suggests(String regex) {
        return new DependencyMatcher("Suggests", RpmInfo::getSuggests, regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches packages with Supplements matching specified
     * regular expression.
     *
     * @param regex regular expression to match against package Supplements
     * @return matcher for package Supplements
     */
    public static Matcher<RpmInfo> supplements(String regex) {
        return new DependencyMatcher("Supplements", RpmInfo::getSupplements, regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches packages with Enhances matching specified
     * regular expression.
     *
     * @param regex regular expression to match against package Enhances
     * @return matcher for package Enhances
     */
    public static Matcher<RpmInfo> enhances(String regex) {
        return new DependencyMatcher("Enhances", RpmInfo::getEnhances, regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches packages with OrderWithRequires matching
     * specified regular expression.
     *
     * @param regex regular expression to match against package OrderWithRequires
     * @return matcher for package OrderWithRequires
     */
    public static Matcher<RpmInfo> orderWithRequires(String regex) {
        return new DependencyMatcher("OrderWithRequires", RpmInfo::getOrderWithRequires, regex);
    }
}
