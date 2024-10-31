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
package mytests.simple;

import static io.kojan.runit.api.RUnit.assertThat;
import static io.kojan.runit.api.RUnit.assumeThat;
import static io.kojan.runit.api.RUnit.sourceRPM;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.PackageTest;
import org.junit.jupiter.api.Test;

/**
 * @author Mikolaj Izdebski
 */
public class SimpleCheck {
    @Test
    void testSkip() {
        assumeThat("some skip logic", true, is(false));
    }

    @Test
    void testPass() {
        // pass
    }

    @PackageTest
    void testFail() {
        assertThat("arithmetic", 42, is(lessThanOrEqualTo(-42)));
    }

    @PackageTest
    void testPackage(RpmInfo rpm) {
        assertThat("name", rpm, is(sourceRPM()));
    }

    @PackageTest
    void testError() {
        throw null;
    }
}
