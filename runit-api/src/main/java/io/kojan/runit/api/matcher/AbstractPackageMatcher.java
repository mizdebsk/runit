package io.kojan.runit.api.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import io.kojan.javadeptools.rpm.RpmInfo;

abstract class AbstractPackageMatcher extends TypeSafeMatcher<RpmInfo> {
    @Override
    protected void describeMismatchSafely(RpmInfo rpm, Description description) {
        description.appendText("was ");
        description.appendText(rpm.isSourcePackage() ? "source RPM" : "binary RPM");
    }
}
