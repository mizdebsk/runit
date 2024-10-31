/*-
 * Copyright (c) 2024 Red Hat, Inc.
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
package io.kojan.runit.api.extension;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;
import io.kojan.runit.api.PackageTest;
import io.kojan.runit.api.context.GlobalContextProvider;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.opentest4j.TestAbortedException;

/**
 * A JUnit Jupiter {@link Extension} that expands <a href=
 * "https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates">test
 * templates</a> annotated with {@link PackageTest}.
 *
 * @author Mikolaj Izdebski
 */
public class PackageTestExtension extends AbstractExtension {
    /** Creates an instance of the JUnit extension. */
    public PackageTestExtension() {}

    /** {@inheritDoc} */
    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
            ExtensionContext context) {
        Method method = context.getRequiredTestMethod();
        Matcher<RpmInfo> packageMatcher = getPackageMatcher(method);
        Predicate<PackageContext> filter = fc -> packageMatcher.matches(fc.getRpmInfo());

        GlobalContext globalContext = GlobalContextProvider.getContext();
        if (globalContext.getPackageSubcontexts().noneMatch(filter)) {
            throw new TestAbortedException("No matching package");
        }
        return globalContext.getPackageSubcontexts().filter(filter).map(PackageTestContext::new);
    }
}
