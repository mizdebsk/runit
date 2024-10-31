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
package io.kojan.runit.validator;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

/**
 * @author Mikolaj Izdebski
 */
class TestCase {
    private final String displayName;
    private final Class<?> testClass;

    public TestCase(String displayName, Class<?> testClass) {
        this.displayName = displayName;
        this.testClass = testClass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void run(TestExecutionListener listener) {
        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequest request =
                LauncherDiscoveryRequestBuilder.request().selectors(selectClass(testClass)).build();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }
}
