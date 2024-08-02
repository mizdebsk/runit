package mytests.compound;

import org.junit.jupiter.api.DisplayName;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.FileTest;
import io.kojan.runit.api.IncludeBinary;
import io.kojan.runit.api.IncludeFileName;
import io.kojan.runit.api.IncludeSource;
import io.kojan.runit.api.PackageTest;

@DisplayName("/TST")
public class CompoundCheck {

    @PackageTest
    @IncludeSource
    void testSourceRPM(RpmInfo rpm) {
    }

    @PackageTest
    @IncludeBinary
    void testBinaryRPM(RpmInfo rpm) {
    }

    @PackageTest
    @IncludeSource("compound(|-.*)")
    void testPatternMatch(RpmInfo rpm) {
    }

    @PackageTest
    @IncludeSource("other")
    void testOther(RpmInfo rpm) {
    }

    @PackageTest
    @IncludeSource("dummy")
    void testNonexistentPackage() {
    }

    @FileTest
    @IncludeFileName(".*\\.jar")
    void testJar(RpmFile ent) {
    }

    @FileTest
    @IncludeFileName("/usr/share/javadoc/(|.*)")
    void testJavadoc(RpmFile ent) {
    }

    @FileTest
    @IncludeFileName("dummy")
    void testNonexistentFile() {
    }
}
