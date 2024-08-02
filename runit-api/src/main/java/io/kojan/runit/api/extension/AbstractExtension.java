package io.kojan.runit.api.extension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.runit.api.ExcludeBinary;
import io.kojan.runit.api.ExcludeDirectory;
import io.kojan.runit.api.ExcludeFileName;
import io.kojan.runit.api.ExcludeRegularFile;
import io.kojan.runit.api.ExcludeSource;
import io.kojan.runit.api.ExcludeSymlink;
import io.kojan.runit.api.IncludeBinary;
import io.kojan.runit.api.IncludeDirectory;
import io.kojan.runit.api.IncludeFileName;
import io.kojan.runit.api.IncludeRegularFile;
import io.kojan.runit.api.IncludeSource;
import io.kojan.runit.api.IncludeSymlink;
import io.kojan.runit.api.matcher.RUnitMatchers;

abstract class AbstractExtension implements TestTemplateInvocationContextProvider {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    private Matcher<RpmInfo> getPackageIncludes(Method method) {
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

    private Matcher<RpmInfo> getPackageExcludes(Method method) {
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

    private Matcher<RpmFile> getFileIncludes(Method method) {
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

    private Matcher<RpmFile> getFileExcludes(Method method) {
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

    Matcher<RpmInfo> getPackageMatcher(Method method) {
        return Matchers.allOf(getPackageIncludes(method), getPackageExcludes(method));
    }

    Matcher<RpmFile> getFileMatcher(Method method) {
        return Matchers.allOf(getFileIncludes(method), getFileExcludes(method));
    }
}
