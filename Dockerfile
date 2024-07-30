FROM docker.io/library/eclipse-temurin:22-jdk-ubi9-minimal AS builder
RUN microdnf -y install git-core maven rpm-libs

WORKDIR /
RUN echo 1
RUN git clone https://github.com/fedora-java/javapackages-validator
WORKDIR /javapackages-validator
RUN mvn -B clean install

COPY . /src
WORKDIR /src
RUN mvn -B clean verify

FROM quay.io/fedora-java/javapackages-validator:2
COPY --from=builder /src/runit-validator/target/lib/* /opt/javapackages-validator/dependency
