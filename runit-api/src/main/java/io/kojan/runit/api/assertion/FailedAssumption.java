package io.kojan.runit.api.assertion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.opentest4j.TestAbortedException;

import io.kojan.runit.api.Mismatch;

class FailedAssumption extends TestAbortedException implements Mismatch {
    private static final long serialVersionUID = 1;
    private final String reason;
    private final Object value;
    private final Matcher<?> matcher;

    public FailedAssumption(String reason, Object value, Matcher<?> matcher) {
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
