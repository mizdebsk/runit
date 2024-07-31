package io.kojan.runit.api.expectation;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

class FailedAssertion extends AssertionError implements UnmetExpectation {
    private static final long serialVersionUID = 1;
    private final String reason;
    private final Object value;
    private final Matcher<?> matcher;

    public FailedAssertion(String reason, Object value, Matcher<?> matcher) {
        this.reason = reason;
        this.value = value;
        this.matcher = matcher;
    }

    @Override
    public String getMessage() {
        return reason;
    }

    @Override
    public Object getActualValue() {
        return value;
    }

    @Override
    public Matcher<?> getExpectationMatcher() {
        return matcher;
    }

    @Override
    public String toString() {
        Description description = new StringDescription();
        MismatchDescriber.describeMismatch(this, description);
        return description.toString();
    }
}
