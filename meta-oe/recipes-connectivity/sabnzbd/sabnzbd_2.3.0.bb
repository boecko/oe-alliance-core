MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=11d738004e69db5356c633936aa54836"

PR = "r3"

DEPENDS = "python"
RDEPENDS_${PN} = "\
    python-core python-shell python-compression python-crypt python-ctypes python-sqlite3 python-sabyenc \
    python-cheetah python-misc python-subprocess python-html python-email python-yenc python-multiprocessing \
    "
RRECOMMENDS_${PN} = "par2cmdline unrar"

SRC_URI = "http://github.com/sabnzbd/sabnzbd/archive/${PV}.tar.gz \
    file://sabnzbd \
    file://sabnzbd.conf \
    file://init-functions \
    "

SRC_URI[md5sum] = "bd63185a90a701385a9dd1b5924f940a"
SRC_URI[sha256sum] = "712ee40770ed35095d2c0b8169cb8176c496b58afb0ea5ebbc2ae111be9d0276"

S = "${WORKDIR}/sabnzbd-${PV}"

INSTALLDIR = "/usr/lib/${PN}"

PACKAGES = "${PN}-doc ${PN}-src ${PN}"

FILES_${PN}-src = "${INSTALLDIR}/*/*.py ${INSTALLDIR}/*/*/*.py"
RDEPENDS_${PN}-src = "python"
FILES_${PN}-doc = "${INSTALLDIR}/*.txt ${INSTALLDIR}/licenses ${INSTALLDIR}/interfaces/*/licenses"
FILES_${PN} = "${INSTALLDIR} /etc/init.d/sabnzbd /etc/init.d/init-functions /etc/enigma2/sabnzbd.conf"

inherit update-rc.d
INITSCRIPT_NAME = "sabnzbd"
INITSCRIPT_PARAMS = "defaults"

do_compile() {
    python -O -m compileall .
}

do_install() {
    install -d ${D}${INSTALLDIR}
    cp -r . ${D}${INSTALLDIR}/
    install -d ${D}/etc/init.d
    install -m 755 ${WORKDIR}/sabnzbd ${D}/etc/init.d/sabnzbd
    install -m 755 ${WORKDIR}/init-functions ${D}/etc/init.d/init-functions
    install -d ${D}/etc/enigma2
    install -m 644 ${WORKDIR}/sabnzbd.conf ${D}/etc/enigma2/sabnzbd.conf
}
