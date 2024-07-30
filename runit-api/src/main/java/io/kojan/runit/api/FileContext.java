package io.kojan.runit.api;

import java.nio.file.Path;

import io.kojan.javadeptools.rpm.RpmFile;

public interface FileContext extends PackageContext {
    Path getFilePath();

    RpmFile getRpmFile();

    byte[] getFileContent();
}
