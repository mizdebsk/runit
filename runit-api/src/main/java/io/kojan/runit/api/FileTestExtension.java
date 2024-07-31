package io.kojan.runit.api;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;
import org.opentest4j.TestAbortedException;

import io.kojan.runit.api.context.GlobalContextProvider;

class FileTestExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

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
