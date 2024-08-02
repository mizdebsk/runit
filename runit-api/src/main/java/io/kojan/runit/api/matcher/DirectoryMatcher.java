package io.kojan.runit.api.matcher;

import org.hamcrest.Description;

import io.kojan.javadeptools.rpm.RpmFile;

class DirectoryMatcher extends AbstractFileMatcher {
    @Override
    protected boolean matchesSafely(RpmFile file) {
        return file.isDirectory();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("directory");
    }
}
