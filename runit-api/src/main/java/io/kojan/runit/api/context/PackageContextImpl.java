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
package io.kojan.runit.api.context;

import io.kojan.javadeptools.rpm.RpmArchiveInputStream;
import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.FileContext;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;

/**
 * @author Mikolaj Izdebski
 */
class ArchiveIterator implements Iterator<CpioArchiveEntry> {
    private final RpmArchiveInputStream stream;
    private CpioArchiveEntry next;
    private boolean closed;

    public ArchiveIterator(RpmArchiveInputStream stream) {
        this.stream = stream;
    }

    @Override
    public boolean hasNext() {
        try {
            if (closed) {
                return false;
            }
            if (next == null) {
                next = stream.getNextEntry();
            }
            if (next == null) {
                stream.close();
                closed = true;
            }
            return next != null;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public CpioArchiveEntry next() {
        try {
            if (next == null) {
                next = stream.getNextEntry();
            }
            return next;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            next = null;
        }
    }
}

/**
 * @author Mikolaj Izdebski
 */
class PackageContextImpl extends GlobalContextImpl implements PackageContext {
    private final RpmPackage rpmPackage;

    public PackageContextImpl(PackageContext packageContext) {
        super(packageContext);
        this.rpmPackage = packageContext.getRpmPackage();
    }

    public PackageContextImpl(GlobalContext globalContext, RpmPackage rpmPackage) {
        super(globalContext);
        this.rpmPackage = rpmPackage;
    }

    @Override
    public RpmPackage getRpmPackage() {
        return rpmPackage;
    }

    private FileContext entryToFileContext(
            RpmArchiveInputStream stream, Map<Path, RpmFile> filesByPath, CpioArchiveEntry entry) {
        try {
            Path path = Path.of(entry.getName());
            if (path.startsWith(Path.of("."))) {
                path = Path.of(".").relativize(path);
            }
            path = Path.of("/").resolve(path);
            RpmFile rpmFile = filesByPath.get(path);
            if (rpmFile == null) {
                throw new IllegalStateException(
                        "null RpmFile for " + path + "; valid are: " + filesByPath.keySet());
            }
            byte[] content = new byte[(int) entry.getSize()];
            stream.read(content);
            return new FileContextImpl(this, rpmFile, content);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private FileContext fileToFileContext(RpmFile rpmFile) {
        return new FileContextImpl(this, rpmFile, null);
    }

    private Path rpmFilePath(RpmFile f) {
        Path p = Path.of(f.getName());
        if (!p.isAbsolute()) {
            p = Path.of("/").resolve(p);
        }
        return p;
    }

    @Override
    public Stream<FileContext> getFileSubcontexts(boolean withContent) {
        try {
            if (withContent) {
                Map<Path, RpmFile> filesByPath = new LinkedHashMap<>();
                rpmPackage.getInfo().getFiles().stream()
                        .forEach(f -> filesByPath.put(rpmFilePath(f), f));
                RpmArchiveInputStream stream = new RpmArchiveInputStream(rpmPackage.getPath());
                Iterable<CpioArchiveEntry> iterable = () -> new ArchiveIterator(stream);
                return StreamSupport.stream(iterable.spliterator(), false)
                        .map(entry -> entryToFileContext(stream, filesByPath, entry));
            } else {
                return rpmPackage.getInfo().getFiles().stream().map(this::fileToFileContext);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
