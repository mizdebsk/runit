package io.kojan.runit.api.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

abstract class AbstractExtension implements TestTemplateInvocationContextProvider {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

}
