package io.kojan.runit.engine;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

class TestCaseImpl implements TestCase {
    private final String displayName;
    private final Class<?> testClass;

    public TestCaseImpl(String displayName, Class<?> testClass) {
        this.displayName = displayName;
        this.testClass = testClass;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void run(TestExecutionListener listener) {
        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().selectors(selectClass(testClass))
                .build();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }
}
