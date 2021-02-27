SUMMARY = "rtaudio"
DESCRIPTION = ""

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.md;md5=371aeb69b82af61e48e185826c4d3bd8"


inherit cmake

DEPENDS += " alsa-lib "

SRC_URI = " http://www.music.mcgill.ca/~gary/rtaudio/release/rtaudio-5.1.0.tar.gz \
	file://cmake-version.patch;patch=1 \
	"

SRC_URI[md5sum] = "20ae9b6454e7c2704467525a6444341b"
SRC_URI[sha256sum] = "ff138b2b6ed2b700b04b406be718df213052d4c952190280cf4e2fab4b61fe09"


S = "${WORKDIR}/${PN}-${PV}"

