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

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * @author Mikolaj Izdebski
 */
class PackageTestParameterResolver implements ParameterResolver {
    private final PackageContext context;

    public PackageTestParameterResolver(PackageContext context) {
        this.context = context;
    }

    @Override
    public boolean supportsParameter(
            ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return resolveParameter(parameterContext, extensionContext) != null;
    }

    @Override
    public Object resolveParameter(
            ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if (type.equals(PackageContext.class)) return context;
        if (type.equals(RpmPackage.class)) return context.getRpmPackage();
        if (type.equals(RpmInfo.class)) return context.getRpmInfo();
        if (type.equals(GlobalContext.class)) return context;
        return null;
    }
}
