package io.kojan.runit.engine.ctx;

import java.nio.file.Path;

import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;

import io.kojan.runit.api.FileContext;
import io.kojan.runit.api.PackageContext;

public class FileContextImpl extends PackageContextImpl implements FileContext {
    private final Path filePath;
    private final CpioArchiveEntry archiveEntry;
    private final byte[] content;

    public FileContextImpl(PackageContext packageContext, Path filePath, CpioArchiveEntry archiveEntry,
            byte[] content) {
        super(packageContext);
        this.filePath = filePath;
        this.archiveEntry = archiveEntry;
        this.content = content;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public CpioArchiveEntry getArchiveEntry() {
        return archiveEntry;
    }

    @Override
    public byte[] getFileContent() {
        return content;
    }
}
