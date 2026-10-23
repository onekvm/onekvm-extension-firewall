# OneKVM firewall extension

Private IPv4/IPv6 iptables tools and the kernel modules required by OneKVM firewall settings. Not installed in the base image.

The manifest, health command, assets, and payload installation rules belong to
this repository. `oe-recipes/` contains its OpenEmbedded layer; the distro pins
both source and layer with `kas/components.lock`.

Build the IPK from `onekvm-distro`:

```sh
make ONEKVM_MACHINE=onekvm-nanokvm build extension firewall
```

To stage the payload locally:

```sh
make install DESTDIR=/tmp/onekvm-firewall-stage PREFIX=/data/onekvm/apps/firewall/0.1.0 VERSION=0.1.0 ARCH=riscv64 XTABLES_BINARY=/path/to/xtables-legacy-multi
```

The extension remains disabled by default. OneKVM settings own configuration;
the health command does not change network interfaces or firewall policy.

License: `GPL-2.0-or-later` for the extension; MIT for OE layer metadata.
