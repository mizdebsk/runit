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

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

/**
 * @author Mikolaj Izdebski
 */
class DiscoveryService {
    private final String namespace;
    private final String classNamePattern;

    public DiscoveryService(String namespace, String classNamePattern) {
        this.namespace = namespace;
        this.classNamePattern = classNamePattern;
    }

    private void configureLogging(Level level) {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(level);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(level);
        }
    }

    public List<TestCase> discoverTestCases() {
        System.err.println();
        System.err.println("=== BEGIN RUNIT TEST DISCOVERY ===");
        configureLogging(Level.FINE);
        List<TestCase> tests = new ArrayList<>();
        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequest request =
                LauncherDiscoveryRequestBuilder.request()
                        .selectors(selectPackage(namespace))
                        .filters(includeClassNamePatterns(classNamePattern))
                        .build();
        TestPlan testPlan = launcher.discover(request);
        for (TestIdentifier id : testPlan.getChildren(testPlan.getRoots().iterator().next())) {
            TestSource ts = id.getSource().get();
            if (ts instanceof ClassSource cs) {
                tests.add(new TestCase(id.getDisplayName(), cs.getJavaClass()));
            }
        }
        configureLogging(Level.INFO);
        System.err.println("=== END RUNIT TEST DISCOVERY ===");
        System.err.println();
        return tests;
    }
}
