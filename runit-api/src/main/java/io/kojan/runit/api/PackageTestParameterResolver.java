package io.kojan.runit.api;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.javadeptools.rpm.RpmPackage;

class PackageTestParameterResolver implements ParameterResolver {

    private final PackageContext context;

    public PackageTestParameterResolver(PackageContext context) {
        this.context = context;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return resolveParameter(parameterContext, extensionContext) != null;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if (type.equals(PackageContext.class))
            return context;
        if (type.equals(RpmPackage.class))
            return context.getRpmPackage();
        if (type.equals(RpmInfo.class))
            return context.getRpmInfo();
        if (type.equals(GlobalContext.class))
            return context;
        return null;
    }
}
