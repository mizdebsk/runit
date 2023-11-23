package io.kojan.runit.api;

import java.util.List;

import io.kojan.javadeptools.rpm.RpmPackage;

public interface GlobalContext {
    List<RpmPackage> getRpmPackages();

    List<PackageContext> getPackageSubcontexts();
}
