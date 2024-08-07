package io.kojan.runit.validator;

import java.util.ArrayList;
import java.util.List;

import org.fedoraproject.javapackages.validator.spi.Result;
import org.fedoraproject.javapackages.validator.spi.Validator;

import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.context.GlobalContextProvider;

class RunitValidator implements Validator {

    private final TestCase test;

    public RunitValidator(TestCase test) {
        this.test = test;
    }

    @Override
    public String getTestName() {
        String dn = test.getDisplayName();
        return dn.startsWith("/") ? dn : "/" + dn;
    }

    @Override
    public Result validate(Iterable<RpmPackage> rpmPackagesIterable, List<String> args) {
        if (args != null && !args.isEmpty()) {
            throw new IllegalArgumentException(
                    "RUnit validator does not support any arguments, but was called with: " + args);
        }
        List<RpmPackage> rpmPackages = new ArrayList<>();
        rpmPackagesIterable.forEach(rpmPackages::add);
        RunitResult result = new RunitResult(test, rpmPackages);
        GlobalContextProvider.setRpmPackages(rpmPackages);
        test.run(result);
        return result.finish();
    }

}
