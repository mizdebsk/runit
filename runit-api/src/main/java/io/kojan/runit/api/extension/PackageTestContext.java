package io.kojan.runit.api.extension;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import io.kojan.runit.api.PackageContext;

class PackageTestContext implements TestTemplateInvocationContext {

    private final PackageContext context;

    public PackageTestContext(PackageContext context) {
        this.context = context;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
        return "[" + context.getRpmInfo() + "]";
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        return Collections.singletonList(new PackageTestParameterResolver(context));
    }

}
