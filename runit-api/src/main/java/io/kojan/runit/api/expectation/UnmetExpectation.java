/*-
 * Copyright (c) 2024-2025 Red Hat, Inc.
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

/**
 * Represents an unmet expectation &ndash; a condition that was expected, but the actual value did
 * not meet the expectation. Typically implemented by throwable classes.
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
