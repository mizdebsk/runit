package io.kojan.runit.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.extension.PackageTestExtension;

/**
 * Annotation that marks method as JUnit <a href=
 * "https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates">test
 * template</a> that is executed once for each RPM package.
 * <p>
 * Package test templates can take arguments of the following types:
 * <ul>
 * <li>{@link PackageContext} &ndash; information about the particular package
 * being tested</li>
 * <li>{@link RpmPackage} &ndash; the RPM package under test</li>
 * <li>{@link RpmInfo} &ndash; the RPM package under test</li>
 * <li>{@link GlobalContext} &ndash; contains information about all the other
 * packages that are tested</li>
 * </ul>
 * 
 * @author Mikolaj Izdebski
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@TestTemplate
@ExtendWith(PackageTestExtension.class)
@Documented
public @interface PackageTest {
}
