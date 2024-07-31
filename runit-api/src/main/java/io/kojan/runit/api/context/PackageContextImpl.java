package io.kojan.runit.api.context;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;

import io.kojan.javadeptools.rpm.RpmArchiveInputStream;
import io.kojan.javadeptools.rpm.RpmFile;
import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.FileContext;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;

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

    private FileContext entryToFileContext(RpmArchiveInputStream stream, Map<Path, RpmFile> filesByPath,
            CpioArchiveEntry entry) {
        try {
            Path path = Paths.get(entry.getName());
            if (path.startsWith(Paths.get("."))) {
                path = Paths.get(".").relativize(path);
            }
            path = Paths.get("/").resolve(path);
            RpmFile rpmFile = filesByPath.get(path);
            if (rpmFile == null) {
                throw new IllegalStateException("null RpmFile for " + path + "; valid are: " + filesByPath.keySet());
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
        Path p = Paths.get(f.getName());
        if (!p.isAbsolute()) {
            p = Paths.get("/").resolve(p);
        }
        return p;
    }

    @Override
    public Stream<FileContext> getFileSubcontexts(boolean withContent) {
        try {
            if (withContent) {
                Map<Path, RpmFile> filesByPath = new LinkedHashMap<>();
                rpmPackage.getInfo().getFiles().stream().forEach(f -> filesByPath.put(rpmFilePath(f), f));
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
