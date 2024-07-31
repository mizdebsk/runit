package io.kojan.runit.validator;

public class TestValidatorFactory extends RunitValidatorFactory {
    static DiscoveryService ds;

    @Override
    DiscoveryService getDiscoveryService() {
        return ds;
    }
}
