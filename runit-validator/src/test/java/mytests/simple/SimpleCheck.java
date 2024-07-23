package mytests.simple;

import static io.kojan.runit.api.RUnit.assertThat;
import static io.kojan.runit.api.RUnit.assumeThat;
import static io.kojan.runit.api.RUnit.sourceRPM;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import org.junit.jupiter.api.Test;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.PackageTest;

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
