package io.kojan.runit.validator;

import java.util.ArrayList;
import java.util.List;

import org.fedoraproject.javapackages.validator.spi.Result;
import org.fedoraproject.javapackages.validator.spi.Validator;

import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.context.GlobalContextImpl;
import io.kojan.runit.api.context.GlobalContextProvider;
import io.kojan.runit.engine.TestCase;

public class RunitValidator implements Validator {

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
    public Result validate(Iterable<RpmPackage> rpmPackagesIterable, List<String> argsIgnored) {
        List<RpmPackage> rpmPackages = new ArrayList<>();
        rpmPackagesIterable.forEach(rpmPackages::add);
        GlobalContext context = new GlobalContextImpl(rpmPackages);
        RunitResult result = new RunitResult(test, context);
        GlobalContextProvider.setContext(context);
        test.run(result);
        return result.finish();
    }

}
