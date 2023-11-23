package io.kojan.runit.main;

import java.io.IOException;
import java.io.PrintWriter;

import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import io.kojan.runit.engine.DiscoveryService;
import io.kojan.runit.engine.TestCase;
import io.kojan.runit.engine.TestRunnerFactory;

class Main {

    public static void main(String[] args) throws IOException {
        System.err.println("Init...");
        Init.init();
        System.err.println("Discovering tests...");
        DiscoveryService ds = new TestRunnerFactory().createDiscoveryService();
        for (TestCase test : ds.discoverTestCases()) {
            System.err.println("Running " + test.getDisplayName());

            SummaryGeneratingListener listener = new SummaryGeneratingListener();
            test.run(listener);
            TestExecutionSummary summary = listener.getSummary();
            // summary.printTo(new PrintWriter(System.err));
            summary.printFailuresTo(new PrintWriter(System.err), 0);
        }
    }

}
