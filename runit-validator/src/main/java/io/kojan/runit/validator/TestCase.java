package io.kojan.runit.validator;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

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
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().selectors(selectClass(testClass))
                .build();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }
}
