package io.kojan.runit.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.fedoraproject.javapackages.validator.Main;
import org.fedoraproject.javapackages.validator.MainTmt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import io.kojan.runit.engine.TestRunnerFactory;

abstract class AbstractValidatorTest {
    @TempDir(cleanup = CleanupMode.ON_SUCCESS)
    Path tempDir;
    Path tmtTree;
    Path tmtTestData;
    Path artifactsDir;

    Path resourcesDir = Paths.get("src/test/resources");
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
        TestValidatorFactory.ds = new TestRunnerFactory().createDiscoveryService(namespace, classNamePattern);
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
            assertTrue(Files.isRegularFile(tmtTestData.resolve(file)), "File " + file + " should exist");
        }
    }

    String readFile(String file) throws Exception {
        assertFiles(file);
        return Files.readString(tmtTestData.resolve(file), StandardCharsets.UTF_8);
    }
}
