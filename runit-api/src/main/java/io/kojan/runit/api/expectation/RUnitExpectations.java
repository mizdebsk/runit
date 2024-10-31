/*-
 * Copyright (c) 2024 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.kojan.runit.api.expectation;

import org.hamcrest.Matcher;
import org.opentest4j.TestAbortedException;

/**
 * Class providing static methods for checking expectations.
 *
 * <p>There are two types of expectations:
 *
 * <ul>
 *   <li>assertions &ndash; when unmet they lead to test failures
 *   <li>assumptions &ndash; when unmet they lead to tests being skipped
 * </ul>
 *
 * @author Mikolaj Izdebski
 */
public class RUnitExpectations {
    private RUnitExpectations() {}

    /**
     * Assert that passed value matches given Hamcrest {@link Matcher}.
     *
     * <p>In case of mismatch, an instance of {@link AssertionError} implementing {@link
     * UnmetExpectation} is raised, which leads to test failure.
     *
     * @param <T> type of the value being matched
     * @param message additional message about expectation
     * @param actual the value being matched
     * @param expectation matcher for expected value
     * @throws AssertionError in case of mismatch
     */
    public static <T> void assertThat(String message, T actual, Matcher<? super T> expectation)
            throws AssertionError {
        if (!expectation.matches(actual)) {
            throw new FailedAssertion(message, actual, expectation);
        }
    }

    /**
     * Assume that passed value matches given Hamcrest {@link Matcher}.
     *
     * <p>In case of mismatch, an instance of {@link TestAbortedException} implementing {@link
     * UnmetExpectation} is raised, which leads to test being skipped.
     *
     * @param <T> type of the value being matched
     * @param message additional message about expectation
     * @param actual the value being matched
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
