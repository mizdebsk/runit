package io.kojan.runit.api;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.kojan.javadeptools.rpm.RpmFile;

class FileContextImpl extends PackageContextImpl implements FileContext {
    private final Path filePath;
    private final RpmFile rpmFile;
    private final byte[] content;

    public FileContextImpl(PackageContext packageContext, RpmFile rpmFile, byte[] content) {
        super(packageContext);
        this.filePath = Paths.get(rpmFile.getName());
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
