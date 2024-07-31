package io.kojan.runit.api.assertion;

import org.hamcrest.Description;

import io.kojan.runit.api.Mismatch;

class MismatchDescriber {
    static void describeMismatch(Mismatch mismatch, Description description) {
        description.appendText(mismatch.getReason()) //
                .appendText(System.lineSeparator()) //
                .appendText("Expected: ") //
                .appendDescriptionOf(mismatch.getMatcher()) //
                .appendText(System.lineSeparator()) //
                .appendText("     but: ");
        mismatch.getMatcher().describeMismatch(mismatch.getValue(), description);
    }
}
