package io.kojan.runit.api;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.assertion.RUnitAssertions;

public class RUnit {
    public static <T> void assertThat(String reason, T value, Matcher<? super T> matcher) {
        RUnitAssertions.assertThat(reason, value, matcher);
    }

    public static <T> void assumeThat(String reason, T value, Matcher<? super T> matcher) {
        RUnitAssertions.assumeThat(reason, value, matcher);
    }

    public static Matcher<RpmInfo> sourceRPM() {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(RpmInfo rpm) {
                return rpm.isSourcePackage();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("source RPM");
            }

            @Override
            protected void describeMismatchSafely(RpmInfo rpm, Description description) {
                description.appendText("was binary RPM ").appendValue(rpm);
            }
        };
    }
}
