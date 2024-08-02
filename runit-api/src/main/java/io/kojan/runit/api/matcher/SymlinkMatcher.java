package io.kojan.runit.api.matcher;

import org.hamcrest.Description;

import io.kojan.javadeptools.rpm.RpmFile;

class SymlinkMatcher extends AbstractFileMatcher {
    @Override
    protected boolean matchesSafely(RpmFile file) {
        return file.isSymbolicLink();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("symbolic link");
    }
}
