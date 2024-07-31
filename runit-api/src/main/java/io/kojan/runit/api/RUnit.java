package io.kojan.runit.api;

import org.hamcrest.Matcher;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.expectation.RUnitExpectations;
import io.kojan.runit.api.matcher.RUnitMatchers;

public class RUnit {
    private RUnit() {
    }

    public static <T> void assertThat(String reason, T value, Matcher<? super T> matcher) {
        RUnitExpectations.assertThat(reason, value, matcher);
    }

    public static <T> void assumeThat(String reason, T value, Matcher<? super T> matcher) {
        RUnitExpectations.assumeThat(reason, value, matcher);
    }

    public static Matcher<RpmInfo> sourceRPM() {
        return RUnitMatchers.sourceRPM();
    }
}
