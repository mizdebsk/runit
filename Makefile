JPV_IMAGE := quay.io/fedora-java/javapackages-validator:2
RUNIT_IMAGE := quay.io/mizdebsk/runit:latest

pull:
	podman pull $(JPV_IMAGE)

build: pull
	podman build -t $(RUNIT_IMAGE) .

push:
	podman push $(RUNIT_IMAGE)

.PHONY: pull build push
