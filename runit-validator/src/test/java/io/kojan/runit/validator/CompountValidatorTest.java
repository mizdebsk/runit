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
package io.kojan.runit.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Mikolaj Izdebski
 */
public class CompountValidatorTest extends AbstractValidatorTest {
    @Test
    void testCompound() throws Exception {
        copyArtifact("compound-42-1.src.rpm");
        copyArtifact("compound-42-1.noarch.rpm");
        copyArtifact("compound-sub-42-1.noarch.rpm");
        copyArtifact("other-42-1.noarch.rpm");

        setupDiscovery("mytests.compound", ".*Check");
        runJPV(0);

        assertFiles("results.yaml", "results/TST.html", "results/TST.log");
        assertTrue(readFile("results.yaml").contains("result: pass"), "result is pass");
    }
}
