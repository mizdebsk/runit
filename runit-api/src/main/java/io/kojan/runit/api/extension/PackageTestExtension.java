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
import org.junit.platform.commons.util.AnnotationUtils;
import org.opentest4j.TestAbortedException;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.ExcludeBinary;
import io.kojan.runit.api.ExcludeSource;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.IncludeBinary;
import io.kojan.runit.api.IncludeSource;
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
public class PackageTestExtension extends AbstractExtension {

    /**
     * Creates an instance of the JUnit extension.
     */
    public PackageTestExtension() {
    }

    private Matcher<RpmInfo> getIncludes(Method method) {
        List<Matcher<? super RpmInfo>> includes = new ArrayList<>();

        Optional<IncludeSource> includeSource = AnnotationUtils.findAnnotation(method, IncludeSource.class);
        if (includeSource.isPresent()) {
            Matcher<RpmInfo> matcher = RUnitMatchers.sourceRPM();
            if (!includeSource.get().value().isBlank()) {
                matcher = Matchers.allOf(matcher, RUnitMatchers.binaryName(includeSource.get().value()));
            }
            includes.add(matcher);
        }

        Optional<IncludeBinary> includeBinary = AnnotationUtils.findAnnotation(method, IncludeBinary.class);
        if (includeBinary.isPresent()) {
            Matcher<RpmInfo> matcher = RUnitMatchers.binaryRPM();
            if (!includeBinary.get().value().isBlank()) {
                matcher = Matchers.allOf(matcher, RUnitMatchers.binaryName(includeBinary.get().value()));
            }
            includes.add(matcher);
        }

        // No includes means include everything
        return includes.isEmpty() ? Matchers.any(RpmInfo.class) : Matchers.anyOf(includes);
    }

    private Matcher<RpmInfo> getExcludes(Method method) {
        List<Matcher<? super RpmInfo>> excludes = new ArrayList<>();

        Optional<ExcludeSource> excludeSource = AnnotationUtils.findAnnotation(method, ExcludeSource.class);
        if (excludeSource.isPresent()) {
            Matcher<RpmInfo> matcher = RUnitMatchers.sourceRPM();
            if (!excludeSource.get().value().isBlank()) {
                matcher = Matchers.allOf(matcher, RUnitMatchers.binaryName(excludeSource.get().value()));
            }
            excludes.add(matcher);
        }

        Optional<ExcludeBinary> excludeBinary = AnnotationUtils.findAnnotation(method, ExcludeBinary.class);
        if (excludeBinary.isPresent()) {
            Matcher<RpmInfo> matcher = RUnitMatchers.binaryRPM();
            if (!excludeBinary.get().value().isBlank()) {
                matcher = Matchers.allOf(matcher, RUnitMatchers.binaryName(excludeBinary.get().value()));
            }
            excludes.add(matcher);
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
