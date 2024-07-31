package io.kojan.runit.api;

import org.hamcrest.Matcher;

public interface Mismatch {
    String getReason();

    Object getValue();

    Matcher<?> getMatcher();
}
