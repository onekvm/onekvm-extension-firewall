HOMEPAGE = "https://github.com/onekvm/onekvm-extension-firewall"
SUMMARY = "Optional OneKVM firewall runtime"
DESCRIPTION = "Private IPv4/IPv6 iptables tools and the kernel modules required by OneKVM firewall settings. Not installed in the base image."
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 file://${ONEKVM_FIREWALL_SOURCE}/LICENSE;md5=261bea1168c0bdfa73232ee90df11eb6"
PR = "r1"

ONEKVM_COMPONENT_GIT_URI ??= "git://github.com/onekvm/onekvm-extension-firewall.git;protocol=https;branch=main"
ONEKVM_COMPONENT_SRCREV ??= ""

# Reuse the distro's upstream iptables release without modifying the system
# iptables package used by NetBird or other network tools.
IPTABLES_VERSION = "1.8.13"
SRC_URI = " \
    ${ONEKVM_COMPONENT_GIT_URI} \
    https://netfilter.org/pub/iptables/iptables-${IPTABLES_VERSION}.tar.xz;name=iptables \
"
SRC_URI[iptables.sha256sum] = "1afcd33da9e8f913ace6a2126788162e207e26f5d5e29c6573c0e581ffc58b99"
SRCREV = "${ONEKVM_COMPONENT_SRCREV}"
S = "${UNPACKDIR}/iptables-${IPTABLES_VERSION}"
ONEKVM_FIREWALL_SOURCE = "${UNPACKDIR}/${BP}"

inherit autotools pkgconfig onekvm-extension

ONEKVM_EXTENSION_ID = "firewall"
ONEKVM_EXTENSION_API_VERSION = "2"
ONEKVM_EXTENSION_MANIFEST_TEMPLATE = "${ONEKVM_FIREWALL_SOURCE}/manifest.json.in"
COMPATIBLE_MACHINE = "^onekvm-"

# Embed the xtables libraries and match/target extensions in one executable.
# Only libc stays dynamic; the runtime survives slot changes with plugin data.
EXTRA_OECONF = " \
    --with-kernel=${STAGING_INCDIR} \
    --enable-ipv6 --enable-static --disable-shared \
    --disable-nftables --disable-libnfnetlink --disable-connlabel \
"
CFLAGS:append:libc-musl = " -D__UAPI_DEF_ETHHDR=0"

do_configure:prepend() {
    rm -f ${S}/libtool.m4 ${S}/lt~obsolete.m4 ${S}/ltoptions.m4 ${S}/ltsugar.m4 ${S}/ltversion.m4
    cp -f ${S}/libiptc/linux_list.h ${S}/include/libiptc/
}

do_install() {
    oe_runmake -C ${ONEKVM_FIREWALL_SOURCE} install \
        DESTDIR=${D} PREFIX=${ONEKVM_EXTENSION_ROOT} VERSION=${PV} ARCH=${TARGET_ARCH} \
        XTABLES_BINARY=${B}/iptables/xtables-legacy-multi
}

RDEPENDS:${PN} += "onekvm-kernel-module-firewall"
