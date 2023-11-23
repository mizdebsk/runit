package io.kojan.runit.engine.ctx;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;

import io.kojan.javadeptools.rpm.RpmArchiveInputStream;
import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.FileContext;
import io.kojan.runit.api.GlobalContext;
import io.kojan.runit.api.PackageContext;

public class PackageContextImpl extends GlobalContextImpl implements PackageContext {
    private final RpmPackage rpmPackage;
    private List<FileContext> subcontexts;

    public PackageContextImpl(PackageContext packageContext) {
        super(packageContext);
        this.rpmPackage = packageContext.getRpmPackage();
    }

    public PackageContextImpl(GlobalContext globalContext, RpmPackage rpmPackage) {
        super(globalContext);
        this.rpmPackage = rpmPackage;
    }

    @Override
    public RpmPackage getRpmPackage() {
        return rpmPackage;
    }

    @Override
    public synchronized List<FileContext> getFileSubcontexts() {
        if (subcontexts == null) {
            subcontexts = new ArrayList<>();
            try (RpmArchiveInputStream stream = new RpmArchiveInputStream(rpmPackage.getPath())) {
                for (CpioArchiveEntry entry; (entry = stream.getNextEntry()) != null;) {
                    Path path = Paths.get(entry.getName());
                    if (path.startsWith(Paths.get("."))) {
                        path = Paths.get(".").relativize(path);
                    }
                    path = Paths.get("/").resolve(path);
                    byte[] content = new byte[(int) entry.getSize()];
                    stream.read(content);
                    subcontexts.add(new FileContextImpl(this, path, entry, content));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return subcontexts;
    }
}
