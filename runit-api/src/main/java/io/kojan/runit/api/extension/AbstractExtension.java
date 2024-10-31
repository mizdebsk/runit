/*-
 * Copyright (c) 2024 Red Hat, Inc.
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;

/**
 * @author Mikolaj Izdebski
 */
abstract class AbstractExtension implements TestTemplateInvocationContextProvider {
    /** {@inheritDoc} */
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    private Matcher<RpmInfo> getPackageIncludes(Method method) {
        List<Matcher<? super RpmInfo>> includes = new ArrayList<>();

        for (IncludeSource includeSource :
                AnnotationUtils.findRepeatableAnnotations(method, IncludeSource.class)) {
            Matcher<RpmInfo> matcher = RUnitMatchers.sourceRPM();
            if (!includeSource.value().isEmpty()) {
                matcher = Matchers.allOf(matcher, RUnitMatchers.sourceName(includeSource.value()));
            }
            includes.add(matcher);
        }

        for (IncludeBinary includeBinary :
                AnnotationUtils.findRepeatableAnnotations(method, IncludeBinary.class)) {
            Matcher<RpmInfo> matcher = RUnitMatchers.binaryRPM();
            if (!includeBinary.value().isEmpty()) {
                matcher = Matchers.allOf(matcher, RUnitMatchers.name(includeBinary.value()));
            }
            includes.add(matcher);
        }

        // No includes means include everything
        return includes.isEmpty() ? Matchers.any(RpmInfo.class) : Matchers.anyOf(includes);
    }

    private Matcher<RpmInfo> getPackageExcludes(Method method) {
        List<Matcher<? super RpmInfo>> excludes = new ArrayList<>();

        for (ExcludeSource excludeSource :
                AnnotationUtils.findRepeatableAnnotations(method, ExcludeSource.class)) {
            Matcher<RpmInfo> matcher = RUnitMatchers.sourceRPM();
            if (!excludeSource.value().isEmpty()) {
                matcher = Matchers.allOf(matcher, RUnitMatchers.sourceName(excludeSource.value()));
            }
            excludes.add(matcher);
        }

        for (ExcludeBinary excludeBinary :
                AnnotationUtils.findRepeatableAnnotations(method, ExcludeBinary.class)) {
            Matcher<RpmInfo> matcher = RUnitMatchers.binaryRPM();
            if (!excludeBinary.value().isEmpty()) {
                matcher = Matchers.allOf(matcher, RUnitMatchers.name(excludeBinary.value()));
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

        for (IncludeFileName includeFileName :
                AnnotationUtils.findRepeatableAnnotations(method, IncludeFileName.class)) {
            includes.add(RUnitMatchers.fileName(includeFileName.value()));
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

        for (ExcludeFileName excludeFileName :
                AnnotationUtils.findRepeatableAnnotations(method, ExcludeFileName.class)) {
            excludes.add(RUnitMatchers.fileName(excludeFileName.value()));
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
