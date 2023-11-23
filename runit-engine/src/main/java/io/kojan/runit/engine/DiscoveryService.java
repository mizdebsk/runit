package io.kojan.runit.engine;

import java.util.List;

public interface DiscoveryService {
    List<TestCase> discoverTestCases();
}
