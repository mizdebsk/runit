package io.kojan.runit.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NopValidatorTest extends AbstractValidatorTest {

    @Test
    void testNOP() throws Exception {

        copyArtifact("simple-1.2.3-1.fc22.x86_64.rpm");

        setupDiscovery("mytests.nop");
        runJPV(0);

        assertFiles("results.yaml", "results/.html", "results/.log");
        assertTrue(readFile("results.yaml").contains("result: warn"), "result is warn");
    }

}
