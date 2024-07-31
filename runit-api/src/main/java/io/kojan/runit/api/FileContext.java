package io.kojan.runit.api;

import java.nio.file.Path;

import io.kojan.javadeptools.rpm.RpmFile;

/**
 * Context in which file tests annotated with {@link FileTest} are ran.
 * <p>
 * Specifies the particular file contained in RPM package on which file tests
 * are ran.
 * 
 * @author Mikolaj Izdebski
 */
public interface FileContext extends PackageContext {
    /**
     * Get path to the particular file on which file test is ran.
     * 
     * @return path to file inside RPM package
     */
    Path getFilePath();

    /**
     * Get the particular file on which file test is ran.
     * 
     * @return the file
     */
    RpmFile getRpmFile();

    /**
     * Get content of the particular file on which file test is ran.
     * 
     * @return byte content of the file
     */
    byte[] getFileContent();
}
