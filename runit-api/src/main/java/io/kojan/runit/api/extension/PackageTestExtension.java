package io.kojan.runit.api.extension;

import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;
import org.opentest4j.TestAbortedException;

import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;
import io.kojan.runit.api.PackageTest;
import io.kojan.runit.api.PackageType;
import io.kojan.runit.api.context.GlobalContextProvider;

/**
 * A JUnit Jupiter {@link Extension} that expands <a href=
 * "https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates">test
 * templates</a> annotated with {@link PackageTest}.
 */
public class PackageTestExtension implements TestTemplateInvocationContextProvider {

    /**
     * Creates an instance of the JUnit extension.
     */
    public PackageTestExtension() {
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
        PackageTest annotation = AnnotationUtils.findAnnotation(method, PackageTest.class).get();

        Predicate<PackageContext> filter = ctx -> true;

        if (!annotation.value().isEmpty()) {
            Pattern pattern = Pattern.compile(annotation.value());
            filter = filter.and(ctx -> pattern.matcher(ctx.getRpmInfo().getName()).matches());
        }

        if (annotation.type() != PackageType.BOTH) {
            Predicate<PackageContext> pred = ctx -> ctx.getRpmInfo().isSourcePackage();
            if (annotation.type() == PackageType.BINARY) {
                pred = pred.negate();
            }
            filter = filter.and(pred);
        }

        GlobalContext globalContext = GlobalContextProvider.getContext();
        if (globalContext.getPackageSubcontexts().noneMatch(filter)) {
            throw new TestAbortedException("No matching package");
        }
        return globalContext.getPackageSubcontexts().filter(filter).map(PackageTestContext::new);
    }
}
