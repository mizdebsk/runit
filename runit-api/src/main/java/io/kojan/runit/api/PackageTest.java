/*-
 * Copyright (c) 2024-2025 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.kojan.runit.api;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.extension.PackageTestExtension;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Annotation that marks method as JUnit <a href=
 * "https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates">test
 * template</a> that is executed once for each RPM package.
 *
 * <p>Package test templates can take arguments of the following types:
 *
 * <ul>
 *   <li>{@link PackageContext} &ndash; information about the particular package being tested
 *   <li>{@link RpmPackage} &ndash; the RPM package under test
 *   <li>{@link RpmInfo} &ndash; the RPM package under test
 *   <li>{@link GlobalContext} &ndash; contains information about all the other packages that are
 *       tested
 * </ul>
 *
 * @author Mikolaj Izdebski
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@TestTemplate
@ExtendWith(PackageTestExtension.class)
@Documented
public @interface PackageTest {}
