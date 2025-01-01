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

import io.kojan.javadeptools.rpm.RpmFile;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Mikolaj Izdebski
 */
abstract class AbstractFileMatcher extends TypeSafeMatcher<RpmFile> {
    @Override
    protected void describeMismatchSafely(RpmFile file, Description description) {
        String type =
                switch (file) {
                    case RpmFile f when f.isRegularFile() -> "regular file";
                    case RpmFile f when f.isDirectory() -> "directory";
                    case RpmFile f when f.isSymbolicLink() -> "symbolic link";
                    default -> "unknown-type file";
                };
        description.appendText("was ");
        description.appendText(type);
    }
}
