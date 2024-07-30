package io.kojan.runit.api;

import java.nio.file.Path;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;

import io.kojan.javadeptools.rpm.RpmFile;

class FileTestParameterResolver extends PackageTestParameterResolver {

    private final FileContext context;

    public FileTestParameterResolver(FileContext context) {
        super(context);
        this.context = context;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if (type.equals(FileContext.class))
            return context;
        if (type.equals(Path.class))
            return context.getFilePath();
        if (type.equals(RpmFile.class))
            return context.getRpmFile();
        if (type.equals(byte[].class))
            return context.getFileContent();
        return super.resolveParameter(parameterContext, extensionContext);
    }
}
