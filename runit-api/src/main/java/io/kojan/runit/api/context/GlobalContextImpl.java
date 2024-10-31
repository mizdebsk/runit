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
package io.kojan.runit.api.context;

import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mikolaj Izdebski
 */
class GlobalContextImpl implements GlobalContext {
    private final List<RpmPackage> rpmPackages;

    public GlobalContextImpl(GlobalContext globalContext) {
        this.rpmPackages = globalContext.getRpmPackages();
    }

    public GlobalContextImpl(List<RpmPackage> rpmPackages) {
        this.rpmPackages = rpmPackages;
    }

    @Override
    public List<RpmPackage> getRpmPackages() {
        return rpmPackages;
    }

    @Override
    public Stream<PackageContext> getPackageSubcontexts() {
        return rpmPackages.stream().map(pkg -> new PackageContextImpl(this, pkg));
    }
}
