package io.kojan.runit.api;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

public interface Mismatch extends SelfDescribing {
    String getReason();

    Object getValue();

    Matcher<?> getMatcher();

    @Override
    default void describeTo(Description description) {
        description.appendText(getReason()) //
                .appendText(System.lineSeparator()) //
                .appendText("Expected: ") //
                .appendDescriptionOf(getMatcher()) //
                .appendText(System.lineSeparator()) //
                .appendText("     but: ");
        getMatcher().describeMismatch(getValue(), description);
    }

}
