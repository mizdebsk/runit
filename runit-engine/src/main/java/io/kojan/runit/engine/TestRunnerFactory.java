package io.kojan.runit.engine;

public class TestRunnerFactory {
    public DiscoveryService createDiscoveryService() {
        return createDiscoveryService("tests", ".*Check");
    }

    public DiscoveryService createDiscoveryService(String namespace, String classNamePattern) {
        return new DiscoveryServiceImpl(namespace, classNamePattern);
    }
}
