package io.kojan.runit.api;

import org.hamcrest.Matcher;
import org.opentest4j.TestAbortedException;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.expectation.RUnitExpectations;
import io.kojan.runit.api.expectation.UnmetExpectation;
import io.kojan.runit.api.matcher.RUnitMatchers;

/**
 * Convenience class providing static methods for various RUnit functionality.
 * <p>
 * It delegates all work other classes, but putting most useful methods in one
 * place allows simplifying tests by not requiring to import all the individual
 * utility classes.
 * 
 * @author Mikolaj Izdebski
 */
public class RUnit {
    private RUnit() {
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
    public static <T> void assertThat(String message, T actual, Matcher<? super T> expectation) {
        RUnitExpectations.assertThat(message, actual, expectation);
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
    public static <T> void assumeThat(String message, T actual, Matcher<? super T> expectation) {
        RUnitExpectations.assumeThat(message, actual, expectation);
    }

    /**
     * Creates a Hamcrest {@link Matcher} that matches source RPM packages.
     * 
     * @return matcher for source RPMs
     */
    public static Matcher<RpmInfo> sourceRPM() {
        return RUnitMatchers.sourceRPM();
    }
}
