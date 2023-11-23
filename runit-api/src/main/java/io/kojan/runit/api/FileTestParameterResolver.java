package io.kojan.runit.api;

import java.nio.file.Path;

import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;

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
        if (type.equals(CpioArchiveEntry.class))
            return context.getArchiveEntry();
        if (type.equals(byte[].class))
            return context.getFileContent();
        return super.resolveParameter(parameterContext, extensionContext);
    }
}
