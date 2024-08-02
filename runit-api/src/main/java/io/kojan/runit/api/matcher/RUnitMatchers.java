package io.kojan.runit.api.matcher;

import org.hamcrest.Matcher;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmInfo;

/**
 * A static class providing factory methods for creating Hamcrest
 * {@link Matcher}s related to RPM packages.
 * 
 * @author Mikolaj Izdebski
 */
public class RUnitMatchers {
    private RUnitMatchers() {
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches RPM packages which source
     * name matching regular expression.
     * 
     * @param regex regular expression to match against package source name
     * @return matcher for package source name
     */
    public static Matcher<RpmInfo> sourceName(String regex) {
        return new SourceNameMatcher(regex);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches RPM packages which name
     * matching regular expression.
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
     * Creates a Hamcrest {@link Matcher} that files which name matching regular
     * expression.
     * 
     * @param regex regular expression to match against file names
     * @return matcher for file names
     */
    public static Matcher<RpmFile> fileName(String regex) {
        return new FileNameMatcher(regex);
    }
}
