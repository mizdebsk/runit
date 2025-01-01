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
package mytests.compound;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.FileTest;
import io.kojan.runit.api.IncludeBinary;
import io.kojan.runit.api.IncludeFileName;
import io.kojan.runit.api.IncludeSource;
import io.kojan.runit.api.PackageTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("/TST")
public class CompoundCheck {
    @PackageTest
    @IncludeSource
    void testSourceRPM(RpmInfo rpm) {}

    @PackageTest
    @IncludeBinary
    void testBinaryRPM(RpmInfo rpm) {}

    @PackageTest
    @IncludeSource("compound(|-.*)")
    void testPatternMatch(RpmInfo rpm) {}

    @PackageTest
    @IncludeSource("other")
    void testOther(RpmInfo rpm) {}

    @PackageTest
    @IncludeSource("dummy")
    void testNonexistentPackage() {}

    @FileTest
    @IncludeFileName(".*\\.jar")
    void testJar(RpmFile ent) {}

    @FileTest
    @IncludeFileName("/usr/share/javadoc/(|.*)")
    void testJavadoc(RpmFile ent) {}

    @FileTest
    @IncludeFileName("dummy")
    void testNonexistentFile() {}
}
