package io.kojan.runit.api.extension;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.opentest4j.TestAbortedException;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.runit.api.FileContext;
import io.kojan.runit.api.FileTest;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.context.GlobalContextProvider;

/**
 * A JUnit Jupiter {@link Extension} that expands <a href=
 * "https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates">test
 * templates</a> annotated with {@link FileTest}.
 * 
 * @author Mikolaj Izdebski
 */
public class FileTestExtension extends AbstractExtension {

    /**
     * Creates an instance of the JUnit extension.
     */
    public FileTestExtension() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        Method method = context.getRequiredTestMethod();
        Matcher<RpmFile> matcher = Matchers.allOf(getFileIncludes(method), getFileExcludes(method));
        Predicate<FileContext> filter = fc -> matcher.matches(fc.getRpmFile());

        boolean withContent = Arrays.asList(method.getParameterTypes()).stream()
                .anyMatch(pt -> byte[].class.isAssignableFrom(pt));
        GlobalContext globalContext = GlobalContextProvider.getContext();
        if (globalContext.getPackageSubcontexts().flatMap(pkgCtx -> pkgCtx.getFileSubcontexts(false))
                .noneMatch(filter)) {
            throw new TestAbortedException("No matching file");
        }
        return globalContext.getPackageSubcontexts() //
                .flatMap(pkgCtx -> pkgCtx.getFileSubcontexts(withContent)) //
                .filter(filter) //
                .map(FileTestContext::new);
    }
}
