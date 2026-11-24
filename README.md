# OneKVM firewall extension

Kernel modules and the nftables dependency required by OneKVM firewall settings. OneKVM installs the policy as its own `inet` table with the system `nft`. Included in the base image.

The manifest, health command, assets, and payload installation rules belong to
this repository. `oe-recipes/` contains its OpenEmbedded layer; the distro pins
both source and layer with `kas/components.lock`.

Build the IPK from `onekvm-distro`:

```sh
make ONEKVM_MACHINE=onekvm-nanokvm build extension firewall
```

To stage the payload locally:

```sh
make install DESTDIR=/tmp/onekvm-firewall-stage PREFIX=/data/onekvm/apps/firewall/0.1.0 VERSION=0.1.0 ARCH=riscv64
```

This environment package is ready after installation and needs no service switch.
Firewall protection remains off by default; configure and enable it in Firewall settings.
The health command does not change network interfaces or firewall policy.

License: `GPL-2.0-or-later` for the extension; MIT for OE layer metadata.
