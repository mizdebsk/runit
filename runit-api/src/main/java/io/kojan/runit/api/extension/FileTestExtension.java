package io.kojan.runit.api.extension;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;
import org.opentest4j.TestAbortedException;

import io.kojan.runit.api.FileContext;
import io.kojan.runit.api.FileTest;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.context.GlobalContextProvider;

/**
 * A JUnit Jupiter {@link Extension} that expands <a href=
 * "https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates">test
 * templates</a> annotated with {@link FileTest}.
 */
public class FileTestExtension implements TestTemplateInvocationContextProvider {

    /**
     * Creates an instance of the JUnit extension.
     */
    public FileTestExtension() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        Method method = context.getRequiredTestMethod();
        FileTest annotation = AnnotationUtils.findAnnotation(method, FileTest.class).get();

        Predicate<FileContext> filter = ctx -> true;

        if (!annotation.value().isEmpty()) {
            Pattern pattern = Pattern.compile(annotation.value());
            filter = filter.and(ctx -> pattern.matcher(ctx.getFilePath().toString()).matches());
        }

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
