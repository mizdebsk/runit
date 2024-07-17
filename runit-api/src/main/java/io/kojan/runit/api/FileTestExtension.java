package io.kojan.runit.api;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

class FileTestExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        GlobalContext globalContext = GlobalContextProvider.getContext();
        return globalContext.getPackageSubcontexts() //
                .flatMap(PackageContext::getFileSubcontexts) //
                .map(FileTestContext::new);
    }
}
