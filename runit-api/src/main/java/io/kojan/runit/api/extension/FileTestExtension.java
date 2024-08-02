package io.kojan.runit.api.extension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.platform.commons.util.AnnotationUtils;
import org.opentest4j.TestAbortedException;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.runit.api.ExcludeDirectory;
import io.kojan.runit.api.ExcludeFileName;
import io.kojan.runit.api.ExcludeRegularFile;
import io.kojan.runit.api.ExcludeSymlink;
import io.kojan.runit.api.FileContext;
import io.kojan.runit.api.FileTest;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.IncludeDirectory;
import io.kojan.runit.api.IncludeFileName;
import io.kojan.runit.api.IncludeRegularFile;
import io.kojan.runit.api.IncludeSymlink;
import io.kojan.runit.api.context.GlobalContextProvider;
import io.kojan.runit.api.matcher.RUnitMatchers;

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

    private Matcher<RpmFile> getIncludes(Method method) {
        List<Matcher<? super RpmFile>> includes = new ArrayList<>();

        if (AnnotationUtils.findAnnotation(method, IncludeRegularFile.class).isPresent()) {
            includes.add(RUnitMatchers.regularFile());
        }

        if (AnnotationUtils.findAnnotation(method, IncludeDirectory.class).isPresent()) {
            includes.add(RUnitMatchers.directory());
        }

        if (AnnotationUtils.findAnnotation(method, IncludeSymlink.class).isPresent()) {
            includes.add(RUnitMatchers.symlink());
        }

        Optional<IncludeFileName> includeFileName = AnnotationUtils.findAnnotation(method, IncludeFileName.class);
        if (includeFileName.isPresent()) {
            includes.add(RUnitMatchers.fileName(includeFileName.get().value()));
        }

        // No includes means include everything
        return includes.isEmpty() ? Matchers.any(RpmFile.class) : Matchers.anyOf(includes);
    }

    private Matcher<RpmFile> getExcludes(Method method) {
        List<Matcher<? super RpmFile>> excludes = new ArrayList<>();

        if (AnnotationUtils.findAnnotation(method, ExcludeRegularFile.class).isPresent()) {
            excludes.add(RUnitMatchers.regularFile());
        }

        if (AnnotationUtils.findAnnotation(method, ExcludeDirectory.class).isPresent()) {
            excludes.add(RUnitMatchers.directory());
        }

        if (AnnotationUtils.findAnnotation(method, ExcludeSymlink.class).isPresent()) {
            excludes.add(RUnitMatchers.symlink());
        }

        Optional<ExcludeFileName> excludeFileName = AnnotationUtils.findAnnotation(method, ExcludeFileName.class);
        if (excludeFileName.isPresent()) {
            excludes.add(RUnitMatchers.fileName(excludeFileName.get().value()));
        }

        return Matchers.not(Matchers.anyOf(excludes));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        Method method = context.getRequiredTestMethod();
        Matcher<RpmFile> matcher = Matchers.allOf(getIncludes(method), getExcludes(method));
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
