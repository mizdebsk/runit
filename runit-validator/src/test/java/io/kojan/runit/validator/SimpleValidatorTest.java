package io.kojan.runit.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
