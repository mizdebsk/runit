package mytests.compound;

import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;
import org.junit.jupiter.api.DisplayName;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.FileTest;
import io.kojan.runit.api.PackageTest;
import io.kojan.runit.api.PackageType;

@DisplayName("/TST")
public class CompoundCheck {

    @PackageTest(type = PackageType.SOURCE)
    void testSourceRPM(RpmInfo rpm) {
    }

    @PackageTest(type = PackageType.BINARY)
    void testBinaryRPM(RpmInfo rpm) {
    }

    @PackageTest("compound(|-.*)")
    void testPatternMatch(RpmInfo rpm) {
    }

    @PackageTest("other")
    void testOther(RpmInfo rpm) {
    }

    @PackageTest("dummy")
    void testNonexistentPackage() {
    }

    @FileTest(".*\\.jar")
    void testJar(CpioArchiveEntry ent) {
    }

    @FileTest("/usr/share/javadoc/(|.*)")
    void testJavadoc(CpioArchiveEntry ent) {
    }

    @FileTest("dummy")
    void testNonexistentFile() {
    }
}
