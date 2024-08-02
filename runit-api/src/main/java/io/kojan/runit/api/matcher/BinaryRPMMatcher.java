package io.kojan.runit.api.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import io.kojan.javadeptools.rpm.RpmInfo;

class BinaryRPMMatcher extends TypeSafeMatcher<RpmInfo> {
    @Override
    protected boolean matchesSafely(RpmInfo rpm) {
        return !rpm.isSourcePackage();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("binary RPM");
    }

    @Override
    protected void describeMismatchSafely(RpmInfo rpm, Description description) {
        description.appendText("was source RPM ").appendValue(rpm);
    }
}
