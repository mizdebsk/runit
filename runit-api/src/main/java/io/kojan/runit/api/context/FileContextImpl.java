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
package io.kojan.runit.api.context;

import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.runit.api.FileContext;
import io.kojan.runit.api.PackageContext;
import java.nio.file.Path;

/**
 * @author Mikolaj Izdebski
 */
class FileContextImpl extends PackageContextImpl implements FileContext {
    private final Path filePath;
    private final RpmFile rpmFile;
    private final byte[] content;

    public FileContextImpl(PackageContext packageContext, RpmFile rpmFile, byte[] content) {
        super(packageContext);
        this.filePath = Path.of(rpmFile.getName());
        this.rpmFile = rpmFile;
        this.content = content;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public RpmFile getRpmFile() {
        return rpmFile;
    }

    @Override
    public byte[] getFileContent() {
        return content;
    }
}
