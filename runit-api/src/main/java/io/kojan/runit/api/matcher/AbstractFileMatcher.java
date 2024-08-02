package io.kojan.runit.api.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import io.kojan.javadeptools.rpm.RpmFile;

abstract class AbstractFileMatcher extends TypeSafeMatcher<RpmFile> {
    @Override
    protected void describeMismatchSafely(RpmFile file, Description description) {
        String type = switch (file) {
        case RpmFile f when f.isRegularFile() -> "regular file";
        case RpmFile f when f.isDirectory() -> "directory";
        case RpmFile f when f.isSymbolicLink() -> "symbolic link";
        default -> "unknown-type file";
        };
        description.appendText("was ");
        description.appendText(type);
    }
}
