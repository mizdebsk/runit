package io.kojan.runit.api.extension;

import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.opentest4j.TestAbortedException;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;
import io.kojan.runit.api.PackageTest;
import io.kojan.runit.api.context.GlobalContextProvider;

/**
 * A JUnit Jupiter {@link Extension} that expands <a href=
 * "https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates">test
 * templates</a> annotated with {@link PackageTest}.
 * 
 * @author Mikolaj Izdebski
 */
public class PackageTestExtension extends AbstractExtension {

    /**
     * Creates an instance of the JUnit extension.
     */
    public PackageTestExtension() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        Method method = context.getRequiredTestMethod();
        Matcher<RpmInfo> matcher = Matchers.allOf(getPackageIncludes(method), getPackageExcludes(method));
        Predicate<PackageContext> filter = fc -> matcher.matches(fc.getRpmInfo());

        GlobalContext globalContext = GlobalContextProvider.getContext();
        if (globalContext.getPackageSubcontexts().noneMatch(filter)) {
            throw new TestAbortedException("No matching package");
        }
        return globalContext.getPackageSubcontexts().filter(filter).map(PackageTestContext::new);
    }
}
