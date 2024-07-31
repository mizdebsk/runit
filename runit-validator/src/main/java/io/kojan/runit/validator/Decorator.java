package io.kojan.runit.validator;

import org.fedoraproject.javapackages.validator.spi.Decorated;

interface Decorator {
    Decorated decorate(Object value);
}
