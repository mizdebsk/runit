/*-
 * Copyright (c) 2024-2025 Red Hat, Inc.
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
import io.kojan.runit.api.FileContext;
import java.nio.file.Path;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;

/**
 * @author Mikolaj Izdebski
 */
class FileTestParameterResolver extends PackageTestParameterResolver {
    private final FileContext context;

    public FileTestParameterResolver(FileContext context) {
        super(context);
        this.context = context;
    }

    @Override
    public Object resolveParameter(
            ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if (type.equals(FileContext.class)) return context;
        if (type.equals(Path.class)) return context.getFilePath();
        if (type.equals(RpmFile.class)) return context.getRpmFile();
        if (type.equals(byte[].class)) return context.getFileContent();
        return super.resolveParameter(parameterContext, extensionContext);
    }
}
