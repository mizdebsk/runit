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

import java.util.ArrayList;
import java.util.List;
import org.fedoraproject.javapackages.validator.spi.Validator;
import org.fedoraproject.javapackages.validator.spi.ValidatorFactory;

/**
 * A factory for creating {@link Validator}s that execute JUnit tests discovered on class path.
 *
 * @author Mikolaj Izdebski
 */
public class RunitValidatorFactory implements ValidatorFactory {
    /** Create an instance of the factory. */
    public RunitValidatorFactory() {}

    private final DiscoveryService ds = new TestRunnerFactory().createDiscoveryService();

    DiscoveryService getDiscoveryService() {
        return ds;
    }

    /**
     * Discover RUnit tests on class path.
     *
     * <p>For each test found, create a {@link Validator} that executes the test on given set of RPM
     * packages. on class path.
     *
     * @return list of validators corresponding to discovered tests
     */
    @Override
    public List<Validator> getValidators() {
        List<TestCase> tests = getDiscoveryService().discoverTestCases();
        List<Validator> validators = new ArrayList<>();
        for (TestCase test : tests) {
            validators.add(new RunitValidator(test));
        }
        if (validators.isEmpty()) {
            validators.add(new NopValidator());
        }
        return validators;
    }
}
