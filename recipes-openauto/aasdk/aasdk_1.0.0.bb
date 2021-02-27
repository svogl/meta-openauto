SUMMARY = "OpenAuto AASDK Android library"
DESCRIPTION = ""

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://Readme.md;md5=e8a561cd593f1c1e364c5059d56c2d32"

inherit cmake

DEPENDS = " gcc-runtime boost libusb openssl protobuf protobuf-native  gtest-native "
#RDEPENDS[aasdk] += " protobuf "

SRC_URI = " file://${PN}-${PV}.tar.gz "

SRC_URI[md5sum] = "06e7da008bfe55dd908cd28eea21322e"


S = "${WORKDIR}/aasdk"


do_install_append() {
    mkdir -p ${D}/usr/include
    cp -r ${S}/include/f1x/ ${D}/usr/include

    mkdir -p ${D}/usr/include/aasdk_proto
    cp -r ${WORKDIR}/build/aasdk_proto/*.h ${D}/usr/include/aasdk_proto
}