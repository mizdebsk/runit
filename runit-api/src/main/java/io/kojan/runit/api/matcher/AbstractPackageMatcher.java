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

import io.kojan.javadeptools.rpm.RpmInfo;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author Mikolaj Izdebski
 */
abstract class AbstractPackageMatcher extends TypeSafeMatcher<RpmInfo> {
    @Override
    protected void describeMismatchSafely(RpmInfo rpm, Description description) {
        description.appendText("was ");
        description.appendText(rpm.isSourcePackage() ? "source RPM" : "binary RPM");
    }
}
