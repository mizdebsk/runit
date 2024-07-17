package io.kojan.runit.api;

import java.util.List;
import java.util.stream.Stream;

import io.kojan.javadeptools.rpm.RpmPackage;

public interface GlobalContext {
    List<RpmPackage> getRpmPackages();

    Stream<PackageContext> getPackageSubcontexts();
}
