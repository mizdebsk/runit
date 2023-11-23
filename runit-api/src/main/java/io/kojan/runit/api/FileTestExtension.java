package io.kojan.runit.api;

import java.util.ArrayList;
import java.util.List;
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
        List<TestTemplateInvocationContext> contexts = new ArrayList<>();
        GlobalContext globalContext = GlobalContextProvider.getContext();
        for (PackageContext packageContext : globalContext.getPackageSubcontexts()) {
            for (FileContext fileContext : packageContext.getFileSubcontexts()) {
                contexts.add(new FileTestContext(fileContext));
            }
        }
        return contexts.stream();
    }
}
