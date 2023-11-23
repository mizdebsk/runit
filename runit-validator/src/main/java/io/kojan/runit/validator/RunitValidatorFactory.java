package io.kojan.runit.validator;

import java.util.ArrayList;
import java.util.List;

import org.fedoraproject.javapackages.validator.spi.Validator;
import org.fedoraproject.javapackages.validator.spi.ValidatorFactory;

import io.kojan.runit.engine.DiscoveryService;
import io.kojan.runit.engine.TestCase;
import io.kojan.runit.engine.TestRunnerFactory;

public class RunitValidatorFactory implements ValidatorFactory {
    @Override
    public List<Validator> getValidators() {
        DiscoveryService ds = new TestRunnerFactory().createDiscoveryService();
        List<TestCase> tests = ds.discoverTestCases();
        List<Validator> validators = new ArrayList<>();
        for (TestCase test : tests) {
            validators.add(new RunitValidator(test));
        }
        return validators;
    }
}
