package io.kojan.runit.validator;

public class TestValidatorFactory extends RunitValidatorFactory {
    static DiscoveryService ds;

    @Override
    protected DiscoveryService getDiscoveryService() {
        return ds;
    }
}
