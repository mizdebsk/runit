FROM docker.io/library/eclipse-temurin:21-jdk-ubi9-minimal AS builder
RUN microdnf -y install git-core maven rpm-libs
COPY . /src
WORKDIR /src
RUN mvn -B clean verify

FROM quay.io/fedora-java/javapackages-validator:2
COPY --from=builder /src/runit-validator/target/lib/* /opt/javapackages-validator/dependency
