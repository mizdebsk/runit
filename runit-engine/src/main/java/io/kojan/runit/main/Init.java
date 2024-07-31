package io.kojan.runit.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.kojan.javadeptools.rpm.RpmPackage;
import io.kojan.runit.api.GlobalContextImpl;
import io.kojan.runit.api.GlobalContextProvider;

class Init {
    public static void init() throws IOException {
        List<Path> rpmPaths = Files.walk(Paths.get("/tmp/test-data")) //
                .filter(Files::isRegularFile) //
                .filter(p -> p.getFileName().toString().endsWith(".rpm")) //
                .collect(Collectors.toList());

        List<RpmPackage> rpmPackages = new ArrayList<>();
        for (Path path : rpmPaths) {
            RpmPackage rpmPackage = new RpmPackage(path);
            rpmPackages.add(rpmPackage);
        }
        GlobalContextProvider.setContext(new GlobalContextImpl(rpmPackages));
    }
}
