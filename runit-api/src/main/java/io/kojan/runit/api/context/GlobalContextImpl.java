package io.kojan.runit.api.context;

import java.util.List;
import java.util.stream.Stream;

import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;

class GlobalContextImpl implements GlobalContext {
    private final List<RpmPackage> rpmPackages;

    public GlobalContextImpl(GlobalContext globalContext) {
        this.rpmPackages = globalContext.getRpmPackages();
    }

    public GlobalContextImpl(List<RpmPackage> rpmPackages) {
        this.rpmPackages = rpmPackages;
    }

    @Override
    public List<RpmPackage> getRpmPackages() {
        return rpmPackages;
    }

    @Override
    public Stream<PackageContext> getPackageSubcontexts() {
        return rpmPackages.stream().map(pkg -> new PackageContextImpl(this, pkg));
    }
}
