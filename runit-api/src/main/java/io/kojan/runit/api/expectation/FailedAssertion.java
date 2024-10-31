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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * @author Mikolaj Izdebski
 */
class FailedAssertion extends AssertionError implements UnmetExpectation {
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
    public String getMessage() {
        return reason;
    }

    @Override
    public Object getActualValue() {
        return value;
    }

    @Override
    public Matcher<?> getExpectationMatcher() {
        return matcher;
    }

    @Override
    public String toString() {
        Description description = new StringDescription();
        MismatchDescriber.describeMismatch(this, description);
        return description.toString();
    }
}
