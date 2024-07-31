package io.kojan.runit.api.expectation;

import org.hamcrest.Matcher;

public interface FailedExpectation {
    Matcher<?> getExpectationMatcher();

    Object getActualValue();

    String getMessage();
}
