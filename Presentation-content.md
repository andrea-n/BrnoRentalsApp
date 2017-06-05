# Prezentace

#1 Tým a zadání projektu

Brno Rentals App

Andrea Navrátilová, Robin Knaur, Jakub Juřena

Aplikace pro rychlé zobrazování a filtrování pronájmů v Brně.
- možnost zobrazení pouze vyhovujích nabídek podle nastavení filtru (dispozice a velikost bytu, městská část)
- přidání hvězdičky/like nabídce
- včetně galerie obrázků, pokud je nabídka obsahuje
- kontaktní tlačítko na email/fb messenger, pokud nabídka obsahuje email/jméno uživatele fb


#2 Postup a popis implementované funkcionality
- Načtení jsonu s nabídkami ze serveru (Retrofit)
- Zobrazení v kartách (Card view, Recycler view)
- Rozklik do detailu nabídky, obsahujícího všechny detaily (Coordinator layout)
- Slidovací galerie (View pager), rozklik obrázků na celou obrazovku
- Zobrazení počtu hvězdiček/liků, přidání hvězdičky, odeslání na server, aktualizace podle vrácených dat, změna buttonu na neaktivní
- Float button pro kontakt (email/fb), float button pro like
- Znovunačtení nabídek, zobrazení/skrytí loaderu (Progress bar)

- Server periodicky načítá nové nabídky a poskytuje je pomocí API mobilní aplikaci
- Data se načítají z Facebookových skupin

- Nabídky se filtrují podle dat přijatých od serveru
- Filtry jsou uložené v SharedPreferences

#3 Technické problémy a jejich řešení

(spousta drobností, na které jsme museli přijít, nic vážného)
- Předávání závislostí (pro větší projekt bych zkusila použít např. Dagger pro DI?)
- Aktualizace dat v Recycler view
- Napozicování float buttonu mezi dvě části karty - negativní margin

- Práce s FB graph API, autentizace OAuth
- Získání struktorovaných dat z nestrukturovaného textu.

- Vytvoření vlastního Number Pickeru pro preference (filtry pro velikost bytu)

#4-6 Obrázky z aplikace
