SUMMARY = "OpenAuto AASDK Android library"
DESCRIPTION = ""

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://Readme.md;md5=e8a561cd593f1c1e364c5059d56c2d32"

inherit cmake

DEPENDS = " gcc-runtime boost libusb openssl protobuf protobuf-native  gtest-native "
RDEPENDS_${PN} = " protobuf "

SRC_URI    = " git://github.com/svogl/aasdk.git;branch=development "
SRCREV = "${AUTOREV}"
#SRCREV = "cf642919bed54b283106767c52060c20826badd1"

S = "${WORKDIR}/git"

do_install_append() {
    mkdir -p ${D}/usr/include
    cp -r ${S}/include/f1x/ ${D}/usr/include

    mkdir -p ${D}/usr/include/aasdk_proto
    cp -r ${WORKDIR}/build/aasdk_proto/*.h ${D}/usr/include/aasdk_proto
}
