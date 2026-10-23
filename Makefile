PREFIX ?= /usr/local/lib/onekvm/extensions/firewall
VERSION ?= 0.1.0
ARCH ?= unknown
XTABLES_BINARY ?=

.PHONY: install
install:
	test -n "$(XTABLES_BINARY)"
	install -d $(DESTDIR)$(PREFIX)/bin
	install -m 0755 onekvm-firewall $(DESTDIR)$(PREFIX)/bin/onekvm-firewall
	install -m 0755 $(XTABLES_BINARY) $(DESTDIR)$(PREFIX)/bin/xtables-legacy-multi
	for name in iptables iptables-save iptables-restore ip6tables ip6tables-save ip6tables-restore; do \
		ln -s xtables-legacy-multi $(DESTDIR)$(PREFIX)/bin/$$name; \
	done
	sed -e 's/@VERSION@/$(VERSION)/g' -e 's/@ARCH@/$(ARCH)/g' manifest.json.in > $(DESTDIR)$(PREFIX)/manifest.json
	chmod 0644 $(DESTDIR)$(PREFIX)/manifest.json
