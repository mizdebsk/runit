package io.kojan.runit.api.expectation;

import org.hamcrest.Matcher;
import org.opentest4j.TestAbortedException;

/**
 * Class providing static methods for checking expectations.
 * <p>
 * There are two types of expectations:
 * <ul>
 * <li>assertions &ndash; when unmet they lead to test failures</li>
 * <li>assumptions &ndash; when unmet they lead to tests being skipped</li>
 * </ul>
 * 
 * @author Mikolaj Izdebski
 */
public class RUnitExpectations {
    private RUnitExpectations() {
    }

    /**
     * Assert that passed value matches given Hamcrest {@link Matcher}.
     * <p>
     * In case of mismatch, an instance of {@link AssertionError} implementing
     * {@link UnmetExpectation} is raised, which leads to test failure.
     * 
     * @param <T>         type of the value being matched
     * @param message     additional message about expectation
     * @param actual      the value being matched
     * @param expectation matcher for expected value
     * @throws AssertionError in case of mismatch
     */
    public static <T> void assertThat(String message, T actual, Matcher<? super T> expectation) throws AssertionError {
        if (!expectation.matches(actual)) {
            throw new FailedAssertion(message, actual, expectation);
        }
    }

    /**
     * Assume that passed value matches given Hamcrest {@link Matcher}.
     * <p>
     * In case of mismatch, an instance of {@link TestAbortedException} implementing
     * {@link UnmetExpectation} is raised, which leads to test being skipped.
     * 
     * @param <T>         type of the value being matched
     * @param message     additional message about expectation
     * @param actual      the value being matched
     * @param expectation matcher for expected value
     * @throws TestAbortedException in case of mismatch
     */
    public static <T> void assumeThat(String message, T actual, Matcher<? super T> expectation)
            throws TestAbortedException {
        if (!expectation.matches(actual)) {
            throw new FailedAssumption(message, actual, expectation);
        }
    }
}
