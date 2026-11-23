HOMEPAGE = "https://github.com/onekvm/onekvm-extension-firewall"
SUMMARY = "OneKVM firewall runtime"
DESCRIPTION = "Kernel modules and the nftables dependency required by OneKVM firewall settings. Included in the base image with protection off by default."
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=261bea1168c0bdfa73232ee90df11eb6"
PR = "r2"

ONEKVM_COMPONENT_GIT_URI ??= "git://github.com/onekvm/onekvm-extension-firewall.git;protocol=https;branch=main"
ONEKVM_COMPONENT_SRCREV ??= ""
SRC_URI = "${ONEKVM_COMPONENT_GIT_URI}"
SRCREV = "${ONEKVM_COMPONENT_SRCREV}"

inherit onekvm-extension

ONEKVM_EXTENSION_ID = "firewall"
ONEKVM_EXTENSION_API_VERSION = "2"
ONEKVM_EXTENSION_MANIFEST_TEMPLATE = "${S}/manifest.json.in"
COMPATIBLE_MACHINE = "^onekvm-"

do_install() {
    oe_runmake -C ${S} install \
        DESTDIR=${D} PREFIX=${ONEKVM_EXTENSION_ROOT} VERSION=${PV} ARCH=${TARGET_ARCH}
}

# OneKVM installs the policy with the system nft.
RDEPENDS:${PN} += "nftables onekvm-kernel-module-firewall"
