package mytests.compound;

import org.junit.jupiter.api.DisplayName;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.FileTest;
import io.kojan.runit.api.IncludeBinaryRPM;
import io.kojan.runit.api.IncludeFileName;
import io.kojan.runit.api.IncludeSourceName;
import io.kojan.runit.api.IncludeSourceRPM;
import io.kojan.runit.api.PackageTest;

@DisplayName("/TST")
public class CompoundCheck {

    @PackageTest
    @IncludeSourceRPM
    void testSourceRPM(RpmInfo rpm) {
    }

    @PackageTest
    @IncludeBinaryRPM
    void testBinaryRPM(RpmInfo rpm) {
    }

    @PackageTest
    @IncludeSourceName("compound(|-.*)")
    void testPatternMatch(RpmInfo rpm) {
    }

    @PackageTest
    @IncludeSourceName("other")
    void testOther(RpmInfo rpm) {
    }

    @PackageTest
    @IncludeSourceName("dummy")
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
