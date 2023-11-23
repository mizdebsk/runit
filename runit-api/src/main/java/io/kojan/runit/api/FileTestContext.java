package io.kojan.runit.api;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

class FileTestContext implements TestTemplateInvocationContext {

    private final FileContext context;

    public FileTestContext(FileContext context) {
        this.context = context;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
        return "[" + context.getRpmInfo() + "@" + context.getFilePath() + "]";
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        return Collections.singletonList(new FileTestParameterResolver(context));
    }

}
