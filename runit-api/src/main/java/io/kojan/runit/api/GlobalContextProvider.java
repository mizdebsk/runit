package io.kojan.runit.api;

public class GlobalContextProvider {
    private static final ThreadLocal<GlobalContext> CONTEXT = new ThreadLocal<>();

    public static GlobalContext getContext() {
        return CONTEXT.get();
    }

    public static void setContext(GlobalContext context) {
        CONTEXT.set(context);
    }
}
