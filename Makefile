PREFIX ?= /usr/local/lib/onekvm/extensions/firewall
VERSION ?= 0.1.0
ARCH ?= unknown

.PHONY: all clean install
all:

clean:

install:
	install -d $(DESTDIR)$(PREFIX)/bin
	install -m 0755 onekvm-firewall $(DESTDIR)$(PREFIX)/bin/onekvm-firewall
	sed -e 's/@VERSION@/$(VERSION)/g' -e 's/@ARCH@/$(ARCH)/g' manifest.json.in > $(DESTDIR)$(PREFIX)/manifest.json
	chmod 0644 $(DESTDIR)$(PREFIX)/manifest.json
