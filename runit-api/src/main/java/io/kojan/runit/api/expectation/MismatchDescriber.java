package io.kojan.runit.api.expectation;

import org.hamcrest.Description;

class MismatchDescriber {
    static void describeMismatch(UnmetExpectation mismatch, Description description) {
        description.appendText(mismatch.getMessage()) //
                .appendText(System.lineSeparator()) //
                .appendText("Expected: ") //
                .appendDescriptionOf(mismatch.getExpectationMatcher()) //
                .appendText(System.lineSeparator()) //
                .appendText("     but: ");
        mismatch.getExpectationMatcher().describeMismatch(mismatch.getActualValue(), description);
    }
}
