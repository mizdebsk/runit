package io.kojan.runit.api;

import java.util.stream.Stream;

import io.kojan.javadeptools.rpm.RpmInfo;
import io.kojan.javadeptools.rpm.RpmPackage;

public interface PackageContext extends GlobalContext {
    RpmPackage getRpmPackage();

    default RpmInfo getRpmInfo() {
        return getRpmPackage().getInfo();
    }

    Stream<FileContext> getFileSubcontexts(boolean withContent);
}
