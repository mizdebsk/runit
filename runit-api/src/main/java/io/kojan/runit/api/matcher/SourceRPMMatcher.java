package io.kojan.runit.api.matcher;

import org.hamcrest.Description;

import io.kojan.javadeptools.rpm.RpmInfo;

class SourceRPMMatcher extends AbstractPackageMatcher {
    @Override
    protected boolean matchesSafely(RpmInfo rpm) {
        return rpm.isSourcePackage();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("source RPM");
    }
}
