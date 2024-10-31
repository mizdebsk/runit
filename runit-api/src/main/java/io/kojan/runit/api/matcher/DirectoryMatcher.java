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
import org.hamcrest.Description;

/**
 * @author Mikolaj Izdebski
 */
class DirectoryMatcher extends AbstractFileMatcher {
    @Override
    protected boolean matchesSafely(RpmFile file) {
        return file.isDirectory();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("directory");
    }
}
