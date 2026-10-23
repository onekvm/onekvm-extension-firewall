# OpenEmbedded layer

This layer packages the OneKVM firewall extension. The integrating distro must
set `ONEKVM_COMPONENT_SRCREV` to the same commit used for this layer.
It supports `wrynose` and depends on the distro-owned `onekvm` layer for the
shared `onekvm-extension` class. Layer metadata is licensed under MIT;
see `COPYING.MIT`. The extension's license is recorded in `../LICENSE`.
