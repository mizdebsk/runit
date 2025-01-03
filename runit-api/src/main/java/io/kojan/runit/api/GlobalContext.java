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
package io.kojan.runit.api;

import io.kojan.javadeptools.rpm.RpmPackage;
import java.util.List;
import java.util.stream.Stream;

/**
 * Context in which RUnit tests are ran.
 *
 * <p>Specifies a set of RPM packages on which tests are ran.
 *
 * @author Mikolaj Izdebski
 */
public interface GlobalContext {
    /**
     * Get list of packages on which tests are ran.
     *
     * @return list of RPM packages
     */
    List<RpmPackage> getRpmPackages();

    /**
     * Produces a {@link Stream} of {@link PackageContext}s that allows iterating over all packages
     * in the global context.
     *
     * @return stream of package contexts
     */
    Stream<PackageContext> getPackageSubcontexts();
}
