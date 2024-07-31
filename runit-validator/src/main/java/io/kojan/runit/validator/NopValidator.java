package io.kojan.runit.validator;

import java.util.List;

import org.fedoraproject.javapackages.validator.spi.LogEntry;
import org.fedoraproject.javapackages.validator.spi.Result;
import org.fedoraproject.javapackages.validator.spi.ResultBuilder;
import org.fedoraproject.javapackages.validator.spi.TestResult;
import org.fedoraproject.javapackages.validator.spi.Validator;

import io.kojan.javadeptools.rpm.RpmPackage;

class NopValidator implements Validator {

    @Override
    public String getTestName() {
        return "/";
    }

    @Override
    public Result validate(Iterable<RpmPackage> rpms, List<String> args) {
        ResultBuilder rb = new ResultBuilder();
        rb.setResult(TestResult.warn);
        rb.addLog(LogEntry.warn("No tests were discovered"));
        return rb.build();
    }

}
