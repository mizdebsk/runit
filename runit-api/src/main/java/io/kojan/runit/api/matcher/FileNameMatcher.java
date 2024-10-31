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
package io.kojan.runit.api.matcher;

import io.kojan.javadeptools.rpm.RpmFile;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.hamcrest.Description;

/**
 * @author Mikolaj Izdebski
 */
class FileNameMatcher extends AbstractFileMatcher {
    private final Pattern pattern;
    private final Predicate<String> predicate;

    public FileNameMatcher(String regex) {
        pattern = Pattern.compile(regex);
        predicate = pattern.asMatchPredicate();
    }

    @Override
    protected boolean matchesSafely(RpmFile file) {
        return predicate.test(file.getName());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("file name matching pattern ");
        description.appendValue(pattern);
    }
}
