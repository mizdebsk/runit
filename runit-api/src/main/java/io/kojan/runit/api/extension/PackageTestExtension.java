package io.kojan.runit.api.extension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;
import org.opentest4j.TestAbortedException;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.ExcludeBinaryName;
import io.kojan.runit.api.ExcludeBinaryRPM;
import io.kojan.runit.api.ExcludeSourceName;
import io.kojan.runit.api.ExcludeSourceRPM;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.IncludeBinaryName;
import io.kojan.runit.api.IncludeBinaryRPM;
import io.kojan.runit.api.IncludeSourceName;
import io.kojan.runit.api.IncludeSourceRPM;
import io.kojan.runit.api.PackageContext;
import io.kojan.runit.api.PackageTest;
import io.kojan.runit.api.context.GlobalContextProvider;
import io.kojan.runit.api.matcher.RUnitMatchers;

/**
 * A JUnit Jupiter {@link Extension} that expands <a href=
 * "https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-templates">test
 * templates</a> annotated with {@link PackageTest}.
 * 
 * @author Mikolaj Izdebski
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

    private Matcher<RpmInfo> getIncludes(Method method) {
        List<Matcher<? super RpmInfo>> includes = new ArrayList<>();

        if (AnnotationUtils.findAnnotation(method, IncludeSourceRPM.class).isPresent()) {
            includes.add(RUnitMatchers.sourceRPM());
        }

        if (AnnotationUtils.findAnnotation(method, IncludeBinaryRPM.class).isPresent()) {
            includes.add(RUnitMatchers.binaryRPM());
        }

        Optional<IncludeSourceName> includeSourceName = AnnotationUtils.findAnnotation(method, IncludeSourceName.class);
        if (includeSourceName.isPresent()) {
            includes.add(RUnitMatchers.sourceName(includeSourceName.get().value()));
        }

        Optional<IncludeBinaryName> includeBinaryName = AnnotationUtils.findAnnotation(method, IncludeBinaryName.class);
        if (includeBinaryName.isPresent()) {
            includes.add(RUnitMatchers.binaryName(includeBinaryName.get().value()));
        }

        // No includes means include everything
        return includes.isEmpty() ? Matchers.any(RpmInfo.class) : Matchers.anyOf(includes);
    }

    private Matcher<RpmInfo> getExcludes(Method method) {
        List<Matcher<? super RpmInfo>> excludes = new ArrayList<>();

        if (AnnotationUtils.findAnnotation(method, ExcludeSourceRPM.class).isPresent()) {
            excludes.add(RUnitMatchers.sourceRPM());
        }

        if (AnnotationUtils.findAnnotation(method, ExcludeBinaryRPM.class).isPresent()) {
            excludes.add(RUnitMatchers.binaryRPM());
        }

        Optional<ExcludeSourceName> excludeSourceName = AnnotationUtils.findAnnotation(method, ExcludeSourceName.class);
        if (excludeSourceName.isPresent()) {
            excludes.add(RUnitMatchers.sourceName(excludeSourceName.get().value()));
        }

        Optional<ExcludeBinaryName> excludeBinaryName = AnnotationUtils.findAnnotation(method, ExcludeBinaryName.class);
        if (excludeBinaryName.isPresent()) {
            excludes.add(RUnitMatchers.binaryName(excludeBinaryName.get().value()));
        }

        return Matchers.not(Matchers.anyOf(excludes));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {

        Method method = context.getRequiredTestMethod();
        Matcher<RpmInfo> matcher = Matchers.allOf(getIncludes(method), getExcludes(method));
        Predicate<PackageContext> filter = fc -> matcher.matches(fc.getRpmInfo());

        GlobalContext globalContext = GlobalContextProvider.getContext();
        if (globalContext.getPackageSubcontexts().noneMatch(filter)) {
            throw new TestAbortedException("No matching package");
        }
        return globalContext.getPackageSubcontexts().filter(filter).map(PackageTestContext::new);
    }
}
