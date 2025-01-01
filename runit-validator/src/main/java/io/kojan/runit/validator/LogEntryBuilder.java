/*-
 * Copyright (c) 2024-2025 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.kojan.runit.validator;

import java.util.ArrayList;
import java.util.List;
import org.fedoraproject.javapackages.validator.spi.Decorated;
import org.fedoraproject.javapackages.validator.spi.LogEntry;
import org.fedoraproject.javapackages.validator.spi.LogEvent;

/**
 * @author Mikolaj Izdebski
 */
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
        return new LogEntry(
                event, pattern.toString(), values.toArray(new Decorated[values.size()]));
    }
}
