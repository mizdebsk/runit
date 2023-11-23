package io.kojan.runit.engine.ctx;

import java.util.ArrayList;
import java.util.List;

import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;

public class GlobalContextImpl implements GlobalContext {
    private final List<RpmPackage> rpmPackages;
    private List<PackageContext> subcontexts;

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
    public synchronized List<PackageContext> getPackageSubcontexts() {
        if (subcontexts == null) {
            subcontexts = new ArrayList<>();
            for (RpmPackage rpmPackage : rpmPackages) {
                subcontexts.add(new PackageContextImpl(this, rpmPackage));
            }
        }
        return subcontexts;
    }
}
