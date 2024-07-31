package io.kojan.runit.engine;

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

public class DiscoveryService {

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
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().selectors(selectPackage(namespace))
                .filters(includeClassNamePatterns(classNamePattern)).build();
        TestPlan testPlan = launcher.discover(request);
        for (TestIdentifier id : testPlan.getChildren(testPlan.getRoots().iterator().next())) {
            TestSource ts = id.getSource().get();
            if (ts instanceof ClassSource cs) {
                tests.add(new TestCaseImpl(id.getDisplayName(), cs.getJavaClass()));
            }
        }
        configureLogging(Level.INFO);
        System.err.println("=== END RUNIT TEST DISCOVERY ===");
        System.err.println();
        return tests;
    }
}
