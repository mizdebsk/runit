package io.kojan.runit.api;

import java.nio.file.Path;

import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;

public interface FileContext extends PackageContext {
    Path getFilePath();

    CpioArchiveEntry getArchiveEntry();

    byte[] getFileContent();
}
