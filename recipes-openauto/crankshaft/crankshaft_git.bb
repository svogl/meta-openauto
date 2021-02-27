#
# Crankshaft if a collection of build scripts, systemd services and helper scripts for openauto.
# the original scripts are designed for a Raspberry Pi environment, so adaptation will be necessary.
#
SUMMARY = "OpenAuto Android integration"
DESCRIPTION = ""

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://README.md;md5=d7ed62b1c6cfd57fb4e01bf8988e05cd"

inherit base

PROVIDES = " ${PN} "
DEPENDS = " openauto "
# RDEPENDS_crankshaft = " openauto bluez5-testtools python bash "
RDEPENDS_${PN} = " openauto bluez5-testtools python python3-dbus bash "
# samba protobuf boost pulseaudio dhcpd aasdk aasdk_proto qt5 "
# + plymouth

SRC_URI = " git://github.com/opencardev/crankshaft.git;branch=crankshaft-ng \
	file://hangup-call \
	"

#SRCREV = "${AUTOREV}"
# this is head revision (@2019-10-22)
SRCREV = "ec74db97a1378da9a9a85611f2671128762b540c"

S = "${WORKDIR}/git"

do_compile() {
    if [ -d  "${WORKDIR}/prebuilts" ] ; then
       (cd  "${WORKDIR}/prebuilts" ; git pull )
    else
    git clone https://github.com/opencardev/prebuilts.git "${WORKDIR}/prebuilts"
    fi
}



do_install() {
    echo "installing crankshaft base stuff"					 

    mkdir -p ${D}/lib/systemd/system/crankshaft
    #    mkdir -p ${D}/etc/systemd/system
    mkdir -p ${D}/opt/crankshaft
    cp -r ${S}/stage3/03-crankshaft-base/files/opt ${D}/

    install -m 0644 -D ${WORKDIR}/git/stage3/03-crankshaft-base/files/etc/systemd/system/* ${D}/lib/systemd/system/crankshaft

    # dhcpd stuff:
    # cp -r ${S}/stage3/03-crankshaft-base/files/lib ${D}/
    # cp -r ${S}/stage3/03-crankshaft-base/files/etc/dhcpd ${D}/etc

    echo "installing crankshaft prebuilts"
   
    # csmt updates
    install -m 0755 -D ${WORKDIR}/prebuilts/csmt/crankshaft ${D}/usr/local/bin/crankshaft
    ln -s /usr/local/bin/crankshaft ${D}/usr/local/bin/csmt 
    
    # gpio2kbd updates
    # install -m 0755 -D ${WORKDIR}/prebuilts/gpio2kbd/gpio2kbd ${D}/opt/crankshaft/gpio2kbd

    # cam_overlay updates
    # if [ ! -d ${D}/opt/crankshaft/cam_overlay ]; then
    mkdir ${D}/opt/crankshaft/cam_overlay
    # fi
    # @TODO: build cam_overlay:
    # install -m 0755 ${WORKDIR}/prebuilts/cam_overlay/cam_overlay.bin ${D}/opt/crankshaft/cam_overlay/cam_overlay.bin
    # chmod 777 ${D}/opt/crankshaft/cam_overlay/cam_overlay.bin
    install -m 0755 ${WORKDIR}/prebuilts/cam_overlay/overlay.png ${D}/opt/crankshaft/cam_overlay/overlay.png
    install -m 0755 ${WORKDIR}/prebuilts/cam_overlay/shader-YUYV.frag ${D}/opt/crankshaft/cam_overlay/shader-YUYV.frag
    install -m 0755 ${WORKDIR}/prebuilts/cam_overlay/shader.frag ${D}/opt/crankshaft/cam_overlay/shader.frag
    install -m 0755 ${WORKDIR}/prebuilts/cam_overlay/shader.vert ${D}/opt/crankshaft/cam_overlay/shader.vert

    # openauto updates -- TODO: update with symlink to /usr/bin/* and set md5 accordingly
    install -m 0755 ${WORKDIR}/prebuilts/openauto/autoapp_helper ${D}/usr/local/bin/autoapp_helper
    # install -m 0755 ${WORKDIR}/prebuilts/openauto/autoapp ${D}/usr/local/bin/autoapp
    # install -m 0755 ${WORKDIR}/prebuilts/openauto/btservice ${D}/usr/local/bin/btservice

    # install -m 0644 ${WORKDIR}/prebuilts/openauto/libaasdk.so ${D}/usr/local/lib/libaasdk.so
    # install -m 0644 ${WORKDIR}/prebuilts/openauto/libaasdk_proto.so ${D}/usr/local/lib/libaasdk_proto.so
    # @TODO: build usbreset:
    # install -m 0755 ${WORKDIR}/prebuilts/usbreset/usbreset ${D}/usr/local/bin/usbreset

    # checksum files
    install -m 0444 ${WORKDIR}/prebuilts/openauto/autoapp.md5 ${D}/usr/local/bin/autoapp.md5
    install -m 0444 ${WORKDIR}/prebuilts/openauto/autoapp_helper.md5 ${D}/usr/local/bin/autoapp_helper.md5
    install -m 0444 ${WORKDIR}/prebuilts/openauto/btservice.md5 ${D}/usr/local/bin/btservice.md5
    # install -m 0444 ${WORKDIR}/prebuilts/openauto/libaasdk.so.md5 ${D}/usr/local/lib/libaasdk.so.md5
    # install -m 0444 ${WORKDIR}/prebuilts/openauto/libaasdk_proto.so.md5 ${D}/usr/local/lib/libaasdk_proto.so.md5
    install -m 0444 ${WORKDIR}/prebuilts/usbreset/usbreset.md5 ${D}/usr/local/bin/usbreset.md5


    # plymouth bootsplash
    # install -d "${D}/usr/share/plymouth/themes/crankshaft"
    # install -m 0644 ${S}/stage3/03-crankshaft-base/files/usr/share/plymouth/themes/crankshaft/* "${D}/usr/share/plymouth/themes/crankshaft"

    # ini files:::
    install -m 0755 -D -d "${D}/boot/crankshaft/custom"
    install -m 0644 -D ${S}/stage3/03-crankshaft-base/files/boot/crankshaft/* ${D}/boot/crankshaft

    ###
    ### BT STUFF::::
    ### 

    # echo "installing crankshaft bluetooth stuff"
    mkdir -p ${D}/lib/systemd/system/crankshaft

    install -m 0644 -D ${S}/stage3/04-crankshaft-bluetooth/files/etc/systemd/system/* ${D}/lib/systemd/system/crankshaft

    mkdir -p ${D}/opt/crankshaft

    install -m 0755 -D ${S}/stage3/04-crankshaft-bluetooth/files/opt/crankshaft/* ${D}/opt/crankshaft
    chmod 0644 ${D}/opt/crankshaft/start_order

    mkdir -p ${D}/usr/local/bin
    install -m 0755 -D  ${S}/stage3/04-crankshaft-bluetooth/files/usr/local/bin/* ${D}/usr/local/bin/
    # override with local copy:
    cp ${WORKDIR}/hangup-call ${D}/usr/local/bin/hangup-call

    ## changed to fit to bluez5-testtools paths::
    ln -s /usr/lib/bluez/test/list-devices ${D}/usr/local/bin/list-devices
    ln -s /usr/lib/bluez/test/monitor-bluetooth ${D}/usr/local/bin/monitor-bluetooth
    ln -s /usr/lib/bluez/test/test-adapter ${D}/usr/local/bin/test-adapter
    ln -s /usr/lib/bluez/test/test-heartrate ${D}/usr/local/bin/test-heartrate
    ln -s /usr/lib/bluez/test/test-manager ${D}/usr/local/bin/test-manager
    ln -s /usr/lib/bluez/test/test-nap ${D}/usr/local/bin/test-nap
    ln -s /usr/lib/bluez/test/test-network ${D}/usr/local/bin/test-network
    ln -s /usr/lib/bluez/test/test-profile ${D}/usr/local/bin/test-profile
    ln -s /usr/lib/bluez/test/test-proximity ${D}/usr/local/bin/test-proximity
    ln -s /usr/lib/bluez/test/test-sap-server ${D}/usr/local/bin/test-sap-server
    ln -s /usr/lib/bluez/test/test-thermometer ${D}/usr/local/bin/test-thermometer
}


FILES_${PN} = " /opt/crankshaft/* /etc/systemd/* /lib/systemd/* /boot/* /usr/*"
