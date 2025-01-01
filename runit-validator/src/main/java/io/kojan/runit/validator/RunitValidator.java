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
package io.kojan.runit.validator;

import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.context.GlobalContextProvider;
import java.util.ArrayList;
import java.util.List;
import org.fedoraproject.javapackages.validator.spi.Result;
import org.fedoraproject.javapackages.validator.spi.Validator;

/**
 * @author Mikolaj Izdebski
 */
class RunitValidator implements Validator {
    private final TestCase test;

    public RunitValidator(TestCase test) {
        this.test = test;
    }

    @Override
    public String getTestName() {
        String dn = test.getDisplayName();
        return dn.startsWith("/") ? dn : "/" + dn;
    }

    @Override
    public Result validate(Iterable<RpmPackage> rpmPackagesIterable, List<String> args) {
        if (args != null && !args.isEmpty()) {
            throw new IllegalArgumentException(
                    "RUnit validator does not support any arguments, but was called with: " + args);
        }
        List<RpmPackage> rpmPackages = new ArrayList<>();
        rpmPackagesIterable.forEach(rpmPackages::add);
        RunitResult result = new RunitResult(test, rpmPackages);
        GlobalContextProvider.setRpmPackages(rpmPackages);
        test.run(result);
        return result.finish();
    }
}
