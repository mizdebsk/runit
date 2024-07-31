package io.kojan.runit.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

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
    /**
     * Optional Java {@link Pattern regular expression} used to limit packages on
     * which the test template is expanded.
     * <p>
     * If specified then tests are ran only on packages with names matching the
     * pattern.
     * <p>
     * Defaults to all packages.
     * 
     * @return regular expression used to filter names of packages, empty string
     *         means no filtering
     */
    String value() default "";

    /**
     * Optional {@link PackageType package type} used to limit packages on which the
     * test template is expanded.
     * <p>
     * If specified then tests are ran only on packages of given type.
     * <p>
     * Defaults to all package types.
     * 
     * @return package type used to filter packages
     */
    PackageType type() default PackageType.BOTH;
}
