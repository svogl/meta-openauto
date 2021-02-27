SUMMARY = "OpenAuto Android integration"
DESCRIPTION = ""

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://Readme.md;md5=32ef6748de2f0d2a39f812e1f6a0cb78"

SRC_URI = " git://github.com/svogl/openauto.git;branch=crankshaft-ng \
	    file://qmediaplayer-backport.patch;patch=1 \
	    file://wlan.patch;patch=1 \
	    file://video-out.patch;patch=1 \
	    \
	    file://openauto-bt.sh \
	    file://openauto-bt.service \
	    file://openauto-ui.sh \
	    file://openauto-ui.service \
	    "

inherit cmake_qt5
#todo: inherit systemd

DEPENDS += " protobuf-native gcc-runtime qtbase rtaudio qtdeclarative qtmultimedia qtconnectivity aasdk taglib gpsd libusb1 "

SRCREV = "${AUTOREV}"


S = "${WORKDIR}/git"


do_install_append() {
    echo "install dist stuff"

    install -m 0644 -D ${WORKDIR}/openauto-bt.service ${D}/lib/systemd/system/openauto-bt.service
    install -m 0755 -D ${WORKDIR}/openauto-bt.sh ${D}/usr/bin/openauto-bt.sh

    install -m 0644 -D ${WORKDIR}/openauto-ui.service ${D}/lib/systemd/system/openauto-ui.service
    install -m 0755 -D ${WORKDIR}/openauto-ui.sh ${D}/usr/bin/openauto-ui.sh
}

FILES_${PN} += " /lib/* /usr/bin/* /etc/* "

