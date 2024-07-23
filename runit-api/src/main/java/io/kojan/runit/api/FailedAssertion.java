package io.kojan.runit.api;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class FailedAssertion extends AssertionError implements Mismatch {
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
    public String getReason() {
        return reason;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Matcher<?> getMatcher() {
        return matcher;
    }

    @Override
    public String toString() {
        Description description = new StringDescription();
        describeTo(description);
        return description.toString();
    }
}
