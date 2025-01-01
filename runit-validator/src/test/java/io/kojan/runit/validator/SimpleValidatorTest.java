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
package io.kojan.runit.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Mikolaj Izdebski
 */
public class SimpleValidatorTest extends AbstractValidatorTest {
    @Test
    void testSimple() throws Exception {
        copyArtifact("simple-1.2.3-1.fc22.src.rpm");
        copyArtifact("simple-1.2.3-1.fc22.x86_64.rpm");

        setupDiscovery("mytests.simple", ".*Check");
        runJPV(0);

        assertFiles("results.yaml", "results/SimpleCheck.html", "results/SimpleCheck.log");
        assertTrue(readFile("results.yaml").contains("result: error"), "result is error");
    }
}
