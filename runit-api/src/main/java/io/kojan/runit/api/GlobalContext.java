package io.kojan.runit.api;

import java.util.List;
import java.util.stream.Stream;

import io.kojan.javadeptools.rpm.RpmPackage;

/**
 * Context in which RUnit tests are ran.
 * <p>
 * Specifies a set of RPM packages on which tests are ran.
 * 
 * @author Mikolaj Izdebski
 */
public interface GlobalContext {
    /**
     * Get list of packages on which tests are ran.
     * 
     * @return list of RPM packages
     */
    List<RpmPackage> getRpmPackages();

    /**
     * Produces a {@link Stream} of {@link PackageContext}s that allows iterating
     * over all packages in the global context.
     * 
     * @return stream of package contexts
     */
    Stream<PackageContext> getPackageSubcontexts();
}
