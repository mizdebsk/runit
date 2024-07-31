package io.kojan.runit.validator;

public class TestRunnerFactory {
    public DiscoveryService createDiscoveryService() {
        return createDiscoveryService("tests", ".*Check");
    }

    public DiscoveryService createDiscoveryService(String namespace, String classNamePattern) {
        return new DiscoveryService(namespace, classNamePattern);
    }
}
