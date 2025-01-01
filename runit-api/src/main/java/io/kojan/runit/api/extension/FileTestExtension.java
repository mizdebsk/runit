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
package io.kojan.runit.api.extension;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.FileContext;
import io.kojan.runit.api.FileTest;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.context.GlobalContextProvider;
import java.lang.reflect.Method;
import java.util.Arrays;
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
 * templates</a> annotated with {@link FileTest}.
 *
 * @author Mikolaj Izdebski
 */
public class FileTestExtension extends AbstractExtension {
    /** Creates an instance of the JUnit extension. */
    public FileTestExtension() {}

    /** {@inheritDoc} */
    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
            ExtensionContext context) {
        Method method = context.getRequiredTestMethod();
        Matcher<RpmInfo> packageMatcher = getPackageMatcher(method);
        Matcher<RpmFile> fileMatcher = getFileMatcher(method);
        Predicate<FileContext> filter =
                fc ->
                        packageMatcher.matches(fc.getRpmInfo())
                                && fileMatcher.matches(fc.getRpmFile());

        boolean withContent =
                Arrays.asList(method.getParameterTypes()).stream()
                        .anyMatch(pt -> byte[].class.isAssignableFrom(pt));
        GlobalContext globalContext = GlobalContextProvider.getContext();
        if (globalContext
                .getPackageSubcontexts()
                .flatMap(pkgCtx -> pkgCtx.getFileSubcontexts(false))
                .noneMatch(filter)) {
            throw new TestAbortedException("No matching file");
        }
        return globalContext
                .getPackageSubcontexts() //
                .flatMap(pkgCtx -> pkgCtx.getFileSubcontexts(withContent)) //
                .filter(filter) //
                .map(FileTestContext::new);
    }
}
