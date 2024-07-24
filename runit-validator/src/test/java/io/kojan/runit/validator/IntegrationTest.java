package io.kojan.runit.validator;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class IntegrationTest extends AbstractValidatorTest {

    @Test
    void testIntegration() throws Exception {

        args.add("-cp");
        args.add("/home/kojan/runit-tests/target/classes");

        setupDiscovery("tests", ".*Check");
        artifactsDir = Paths.get("/tmp/test-data");
        runJPV(0);
    }

}
