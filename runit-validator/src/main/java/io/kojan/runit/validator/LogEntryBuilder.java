package io.kojan.runit.validator;

import java.util.ArrayList;
import java.util.List;

import org.fedoraproject.javapackages.validator.spi.Decorated;
import org.fedoraproject.javapackages.validator.spi.LogEntry;
import org.fedoraproject.javapackages.validator.spi.LogEvent;

class LogEntryBuilder {

    private final LogEvent event;
    private final StringBuilder pattern = new StringBuilder();
    private final List<Decorated> values = new ArrayList<>();

    public LogEntryBuilder(LogEvent event) {
        this.event = event;
    }

    public LogEntryBuilder append(Decorated value) {
        pattern.append('{').append(values.size()).append('}');
        values.add(value);
        return this;
    }

    public LogEntryBuilder append(char c) {
        pattern.append(c);
        return this;
    }

    public LogEntryBuilder append(String text) {
        pattern.append(text);
        return this;
    }

    public LogEntry build() {
        return new LogEntry(event, pattern.toString(), values.toArray(new Decorated[values.size()]));
    }
}
