package io.kojan.runit.api.assertion;

import org.hamcrest.Matcher;

public interface FailedExpectation {
    Matcher<?> getExpectationMatcher();

    Object getActualValue();

    String getMessage();
}
