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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.fedoraproject.javapackages.validator.Main;
import org.fedoraproject.javapackages.validator.MainTmt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

/**
 * @author Mikolaj Izdebski
 */
abstract class AbstractValidatorTest {
    @TempDir(cleanup = CleanupMode.ON_SUCCESS)
    Path tempDir;

    Path tmtTree;
    Path tmtTestData;
    Path artifactsDir;

    Path resourcesDir = Path.of("src/test/resources");
    Main main;
    List<String> args;

    void copyArtifact(String name) throws IOException {
        Files.copy(resourcesDir.resolve(name), artifactsDir.resolve(name));
    }

    Path createDir(String name) throws Exception {
        Path dir = tempDir.resolve(name);
        System.err.println(name + ": " + dir);
        Files.createDirectory(dir);
        return dir;
    }

    @BeforeEach
    void initJPV() throws Exception {
        tmtTree = createDir("TMT_TREE");
        tmtTestData = createDir("TMT_TEST_DATA");
        artifactsDir = createDir("TEST_ARTIFACTS");
        main = MainTmt.create(tmtTestData, tmtTree);
        args = new ArrayList<>();
        args.add("-x");
    }

    void setupDiscovery(String namespace, String classNamePattern) {
        TestValidatorFactory.ds =
                new TestRunnerFactory().createDiscoveryService(namespace, classNamePattern);
    }

    void runJPV(int expRc) throws Exception {
        args.add("-f");
        args.add(artifactsDir.toString());
        args.add(TestValidatorFactory.class.getCanonicalName());

        int rc = main.run(args.toArray(new String[args.size()]));
        assertEquals(expRc, rc, "expected return code");
    }

    void assertFiles(String... files) {
        for (String file : files) {
            assertTrue(
                    Files.isRegularFile(tmtTestData.resolve(file)),
                    "File " + file + " should exist");
        }
    }

    String readFile(String file) throws Exception {
        assertFiles(file);
        return Files.readString(tmtTestData.resolve(file), StandardCharsets.UTF_8);
    }
}
