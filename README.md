# asseteer
Lightweight Asset Management
Projektförslag, tänkt att pågå så långt vi hinner, kanske genom forsätningskursen.
# Deltagare
elofturtle
produktpelle
# Roadmap
Det övergripande målet är att skapa en applikation för att hantera diverse tillgångar, som får genomgå flera iterationer/implementationer.
## Vilka assets?
* Globala grupper <--> Teknisk applikation
* CycloneDX & SPDX --> Säkerhetshantering, säka vem som använder vilket beroende
* Tekniska applikationer, till exempel Chrome, vem äger den, var finns systemdokumentation, etc.
## Iterationer (tänkt livscykel för applikaionen)
1 Enanvändarsystem utan db
  * Kan läsa & spara data i form av XML, CSV.
  * Sparar sitt globala state som XML
  * Konfiguration
  * menybaserad nmatning via konsol
2 Enanvändarsystem med lokal databas (sqlite e.d. istället för XML för state) 
3 Kunna söka på flera olika sätt, inklusive med wildcard
4 Riktig databas, programmet kan koppla upp sig mot den även om den inte ligger på localhost
5 Söd för REST-gränssnitt, svar/input via JSON primärt
6 Enkelt webbgränsnitt för visning, sökning, inmatning
7 "rikig" deployment på extern server
8 Fleranvändarstöd, sessionshantering, säkra att man inte skriver över varandras redigeringar.
9 Behörighetshantering (admin/write/read)
