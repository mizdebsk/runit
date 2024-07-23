package io.kojan.runit.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CompountValidatorTest extends AbstractValidatorTest {

    @Test
    void testCompound() throws Exception {

        copyArtifact("compound-42-1.src.rpm");
        copyArtifact("compound-42-1.noarch.rpm");
        copyArtifact("compound-sub-42-1.noarch.rpm");
        copyArtifact("other-42-1.noarch.rpm");

        setupDiscovery("mytests.compound");
        runJPV(0);

        assertFiles("results.yaml", "results/TST.html", "results/TST.log");
        assertTrue(readFile("results.yaml").contains("result: pass"), "result is pass");
    }

}
