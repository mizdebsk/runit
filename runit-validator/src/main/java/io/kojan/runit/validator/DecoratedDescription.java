/*-
 * Copyright (c) 2024 Red Hat, Inc.
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

import org.fedoraproject.javapackages.validator.spi.Decorated;
import org.hamcrest.BaseDescription;
import org.hamcrest.Description;

/**
 * @author Mikolaj Izdebski
 */
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
            log.append(Decorated.plain(s));
        }
    }

    @Override
    protected void append(char c) {
        if (sb != null) {
            sb.append(c);
        } else {
            log.append(Decorated.plain(c));
        }
    }
}
