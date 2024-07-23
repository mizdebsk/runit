package io.kojan.runit.validator;

import org.fedoraproject.javapackages.validator.spi.Decorated;

public interface Decorator {
    Decorated decorate(Object value);
}
