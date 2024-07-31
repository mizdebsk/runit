package io.kojan.runit.engine;

import org.junit.platform.launcher.TestExecutionListener;

public interface TestCase {
    String getDisplayName();

    void run(TestExecutionListener listener);
}
