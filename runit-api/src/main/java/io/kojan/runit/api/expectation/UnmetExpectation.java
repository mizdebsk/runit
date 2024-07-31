package io.kojan.runit.api.expectation;

import org.hamcrest.Matcher;

/**
 * Represents an unmet expectation &ndash; a condition that was expected, but
 * the actual value did not meet the expectation. Typically implemented by
 * throwable classes.
 * 
 * @author Mikolaj Izdebski
 */
public interface UnmetExpectation {
    /**
     * Get Hamcrest {@link Matcher} representing expected condition.
     * 
     * @return matcher for expected value
     */
    Matcher<?> getExpectationMatcher();

    /**
     * Get the actual unexpected value that was found.
     * 
     * @return actual unexpected value
     */
    Object getActualValue();

    /**
     * Get additional message describing expectation mismatch.
     * 
     * @return additional message about the failed expectation
     */
    String getMessage();
}
