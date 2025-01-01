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
package io.kojan.runit.api.matcher;

import io.kojan.javadeptools.rpm.RpmDependency;
import io.kojan.javadeptools.rpm.RpmInfo;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.hamcrest.Description;

/**
 * @author Mikolaj Izdebski
 */
class DependencyMatcher extends AbstractPackageMatcher {
    private final String kind;
    private final Function<RpmInfo, List<RpmDependency>> getter;
    private final Pattern pattern;
    private final Predicate<List<RpmDependency>> predicate;

    public DependencyMatcher(
            String kind, Function<RpmInfo, List<RpmDependency>> getter, String regex) {
        this.kind = kind;
        this.getter = getter;
        pattern = Pattern.compile(regex);
        Predicate<String> depPredicate = pattern.asMatchPredicate();
        predicate = deps -> deps.stream().map(RpmDependency::toString).anyMatch(depPredicate);
    }

    @Override
    protected boolean matchesSafely(RpmInfo rpm) {
        return predicate.test(getter.apply(rpm));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("package that ");
        description.appendText(kind);
        description.appendText(" ");
        description.appendValue(pattern);
    }

    @Override
    protected void describeMismatchSafely(RpmInfo rpm, Description description) {
        super.describeMismatchSafely(rpm, description);
        description.appendText(" with the following ");
        description.appendText(kind);
        description.appendText(":");
        String separator = "\n" + " ".repeat(14) + kind + ": ";
        description.appendValueList(separator, separator, "", getter.apply(rpm));
    }
}
