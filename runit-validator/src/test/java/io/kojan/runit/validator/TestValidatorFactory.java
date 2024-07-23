package io.kojan.runit.validator;

import io.kojan.runit.engine.DiscoveryService;

public class TestValidatorFactory extends RunitValidatorFactory {
    static DiscoveryService ds;

    @Override
    protected DiscoveryService getDiscoveryService() {
        return ds;
    }
}
