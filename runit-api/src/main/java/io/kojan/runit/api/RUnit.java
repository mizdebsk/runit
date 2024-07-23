package io.kojan.runit.api;

import org.hamcrest.Matcher;

public class RUnit {
    public static <T> void assertThat(String reason, T value, Matcher<? super T> matcher) {
        if (!matcher.matches(value)) {
            throw new FailedAssertion(reason, value, matcher);
        }
    }

    public static <T> void assumeThat(String reason, T value, Matcher<? super T> matcher) {
        if (!matcher.matches(value)) {
            throw new FailedAssumption(reason, value, matcher);
        }
    }
}
