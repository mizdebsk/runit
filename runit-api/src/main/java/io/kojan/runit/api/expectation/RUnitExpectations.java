package io.kojan.runit.api.expectation;

import org.hamcrest.Matcher;

public class RUnitExpectations {
    private RUnitExpectations() {
    }

    public static <T> void assertThat(String message, T actual, Matcher<? super T> expectation) {
        if (!expectation.matches(actual)) {
            throw new FailedAssertion(message, actual, expectation);
        }
    }

    public static <T> void assumeThat(String message, T actual, Matcher<? super T> expectation) {
        if (!expectation.matches(actual)) {
            throw new FailedAssumption(message, actual, expectation);
        }
    }
}
