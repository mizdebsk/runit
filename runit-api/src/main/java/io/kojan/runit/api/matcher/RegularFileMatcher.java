package io.kojan.runit.api.matcher;

import org.hamcrest.Description;

import io.kojan.javadeptools.rpm.RpmFile;

class RegularFileMatcher extends AbstractFileMatcher {
    @Override
    protected boolean matchesSafely(RpmFile file) {
        return file.isRegularFile();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("regular file");
    }
}
