package io.kojan.runit.engine;

public class TestRunnerFactory {
    public DiscoveryService createDiscoveryService() {
        return new DiscoveryServiceImpl();
    }
}
