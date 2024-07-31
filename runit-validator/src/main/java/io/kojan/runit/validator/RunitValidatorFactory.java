package io.kojan.runit.validator;

import java.util.ArrayList;
import java.util.List;

import org.fedoraproject.javapackages.validator.spi.Validator;
import org.fedoraproject.javapackages.validator.spi.ValidatorFactory;

public class RunitValidatorFactory implements ValidatorFactory {

    private final DiscoveryService ds = new TestRunnerFactory().createDiscoveryService();

    protected DiscoveryService getDiscoveryService() {
        return ds;
    }

    @Override
    public List<Validator> getValidators() {
        List<TestCase> tests = getDiscoveryService().discoverTestCases();
        List<Validator> validators = new ArrayList<>();
        for (TestCase test : tests) {
            validators.add(new RunitValidator(test));
        }
        if (validators.isEmpty()) {
            validators.add(new NopValidator());
        }
        return validators;
    }
}
