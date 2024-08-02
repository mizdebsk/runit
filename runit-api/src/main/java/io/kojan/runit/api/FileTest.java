package io.kojan.runit.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.extension.FileTestExtension;

/**
 * Annotation that marks method as a JUnit <a href=
 * "https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates">test
 * template</a> that is executed once for each file contained in each RPM
 * package.
 * <p>
 * File test templates can take arguments of the following types:
 * <ul>
 * <li>{@link FileContext} &ndash; information about the particular file being
 * tested</li>
 * <li>{@link Path} &ndash; file path inside the RPM package</li>
 * <li>{@link RpmFile} &ndash; the RPM file under test</li>
 * <li>{@code byte[]} &ndash; contents of the RPM file</li>
 * <li>{@link PackageContext} &ndash; information about the package that
 * contains the file being tested</li>
 * <li>{@link RpmPackage} &ndash; RPM package containing the file under
 * test</li>
 * <li>{@link RpmInfo} &ndash; RPM package containing the file under test</li>
 * <li>{@link GlobalContext} &ndash; contains information about all the other
 * packages that are tested</li>
 * </ul>
 * 
 * @author Mikolaj Izdebski
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@TestTemplate
@ExtendWith(FileTestExtension.class)
@Documented
public @interface FileTest {
}
