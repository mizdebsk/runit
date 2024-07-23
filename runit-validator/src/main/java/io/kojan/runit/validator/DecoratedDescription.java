package io.kojan.runit.validator;

import org.hamcrest.BaseDescription;
import org.hamcrest.Description;

class DecoratedDescription extends BaseDescription {

    private final LogEntryBuilder log;
    private StringBuilder sb;
    private final Decorator decorator;

    public DecoratedDescription(LogEntryBuilder log, Decorator decorator) {
        this.log = log;
        this.decorator = decorator;
    }

    @Override
    public Description appendValue(Object value) {
        if (sb == null) {
            sb = new StringBuilder();
            super.appendValue(value);
            log.append(decorator.decorate(sb.toString()));
            sb = null;
        } else {
            super.appendValue(value);
        }
        return this;
    }

    @Override
    protected void append(String s) {
        if (sb != null) {
            sb.append(s);
        } else {
            log.append(s);
        }
    }

    @Override
    protected void append(char c) {
        if (sb != null) {
            sb.append(c);
        } else {
            log.append(c);
        }
    }

}
