# Asseteer
Lightweight Asset Management

Se vidare https://elofturtle.github.io/asseteer-pages/ 

# Kom igång
## Bygg projektet
Projektet använder maven för beroendehantering.

Du behöver installera maven på egen hand, men resten av beroendena tas om hand av maven.

Resultatet *asseteer-<version>.jar* skapas i katalogen  _target_

```bash
mvn package # kompilerar och kör tester
```

För att bygga den inbyggda webbplatsen med information för utvecklaren så använder du

```bash
mvn site
```

Det skapar en katalog  _site_ utanför projektroten, som sedan kan publiceras om så önskas.

Observera att genereringen av siten ännu inte är helt felfri :)

## Starta Asseteer
Det är en CLI-applikation och du behöver ha Java 21 eller senare installerat i förväg.

Om du står i projekt-roten startar du programmet med

```bash
java -jar ./target/asseteer-<version>.jar
```

Du kan behöva justera sökvägen annars.

När du starta programmet så ska det se ut ungefär enligt nedan:

```bash
Administratör@INV001000112384 MINGW64 ~/git/asseteer (main)
$ java -jar  target/asseteer-0.5.0.jar
### MAIN MENU ###
(1)     Add asset
(2)     Edit asset
(3)     Remove asset
(4)     List assets
(5)     Search asset
(6)     Inverted search
(7)     Importera tillg▒ngar
(8)     Hj▒lp
(9)     Quit
Enter your choice (1 to 9):

```

# Deltagare
elofturtle
produktpelle
# Roadmap
Det övergripande målet är att skapa en applikation för att hantera diverse tillgångar, som får genomgå flera iterationer/implementationer.
## Vilka assets?
* Globala grupper <--> Teknisk applikation
* CycloneDX & SPDX --> Säkerhetshantering, säka vem som använder vilket beroende
* Tekniska applikationer, till exempel Chrome, vem äger den, var finns systemdokumentation, etc.
## Iterationer (tänkt livscykel för applikationen)
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

### Iteration 1 
Kunna konsumera ett antal cyclonedx-xml:er från en mapp (se tex https://github.com/CycloneDX/bom-examples ) genom att ange sökväg på kommandoraden

Komma fram till format för klientapplikationer (vilka uppfiter den delen behöver kunna hantera

Kunna mata in klentapplikationsdata från en csv genom att ange sökväg på kommandoraden

Kunna mata in, söka, lista, redigera, utgå från tidigare information om klientapplikation

Grundläggande konfiguration för applikationen (hårdkodat, tex var leta efter xml:er för cyclonedx) så att man slipper ange det på kommandoraden

```mermaid
classDiagram
Asset <|-- SBOM
    Asset <|-- Programvara
    Asset <|-- Licens
    Asset "0..n" *-- Dependency : dependencies

Dependency

    <<abstract>> Asset
    class Asset{
        -String name
        -Dependency[] dependencies
        +String getUUID()
        +String getName()
        +void setName(Sring name)
        +int compareTo(Asset o)
    }
class Dependency{
    -String UUID
}
```


# Länkar
## SoftWare IDentification Tag (SWID)
https://scribesecurity.com/blog/spdx-vs-cyclonedx-sbom-formats-compared/
https://pages.nist.gov/swid-tools/
https://github.com/Labs64/swid-generator
https://github.com/swidtags/rpm2swidtag/tree/master
Spara state och hantera ev identisk information som ligger i mapp som läses in (är det sparat state eller indatat som gäller i vilken ordning?)

globala grupper p.s.s. som för klientappliaktioner.

Kunna söka & visualisera beroenden cyclonedx
validera xml https://www.baeldung.com/java-validate-xml-xsd 


# Hur tror Chat GPT att det här projektet ser ut?
## UML-diagram
![slightl wrong uml diagram](images/asseteer_uml_class_diagram.png "UML") 
## Interactions
![Pretty horrible interaction diagram](images/asseteer_ineraction_diagram.png "Interactions") 
## Gick det bra?
Nej, gpt:s forte verkar inte vara den sortens kodanalys.

I eclipse verkar stödet halvt försvunnit, så numera är det kanske främst kommersiella alternativ som gäller. 

Om man nu inte går all in och installerar Papyrus.

# Hur projektet faktiskt ser ut
```mermaid
classDiagram
direction BT
class Asset {
  + Asset(String, ArrayList~Dependency~) 
  + Asset() 
  + Asset(String) 
  + setName(String) void
  + getAssetType() String
  + setId(String) void
  + getDependencies() ArrayList~Dependency~
  + setImportant(boolean) void
  + getName() String
  + setIdFromDeserialization(String) void
  + removeDependency(Asset) void
  + addDependency(Dependency) void
  + addDependency(Asset) void
  + toString() String
  + toRepresentation() String
  + equals(Object) boolean
  + hashCode() int
  + getId() String
  + isImportant() boolean
  + setDependencies(ArrayList~Dependency~) void
  + compareTo(Asset) int
  + setAssetType() void
  + removeDependency(Dependency) void
  + toDependency() Dependency
}
class AssetFileReader {
  + AssetFileReader() 
  + readFromFile(String) ArrayList~Asset~
}
class Asseteer {
  + Asseteer(String) 
  + Asseteer() 
  + Scanner scanner
  + readState() void
  + main(String[]) void
  + saveState() void
}
class Dependency {
  + Dependency(Asset) 
  + Dependency() 
  + Dependency(String) 
  + getId() String
  + hashCode() int
  + equals(Object) boolean
  + setId(String) void
  + compareTo(Dependency) int
  + toString() String
}
class PEBKAC {
  + PEBKAC(String, Throwable) 
  + PEBKAC(String) 
  + PEBKAC() 
  + PEBKAC(Throwable) 
  + PEBKAC(String, Throwable, boolean, boolean) 
}
class Programvara {
  + Programvara(String, String, String, String) 
  + Programvara(String, ArrayList~Dependency~) 
  + Programvara(String, String, String, String, ArrayList~Dependency~) 
  + Programvara() 
  + Programvara(String) 
  + setVersion(String) void
  + setName(String) void
  + getVersion() String
  + getÄgare() String
  + setÄgare(String) void
  + getLeverantör() String
  + setLeverantör(String) void
}
class ProgramvaraFileReader {
  + ProgramvaraFileReader() 
  + readFromFile(String) ArrayList~Asset~
}
class SBOM {
  + SBOM(String, String) 
  + SBOM() 
}
class SBOMFileReader {
  + SBOMFileReader() 
  + readFromFile(String) ArrayList~Asset~
}
class WeirdFileException {
  + WeirdFileException() 
  + WeirdFileException(String, Throwable) 
  + WeirdFileException(String, Throwable, boolean, boolean) 
  + WeirdFileException(Throwable) 
  + WeirdFileException(String) 
}
class XmlUtil {
  + XmlUtil() 
  + serialize(ArrayList~Asset~) String
  + deserialize(String) ArrayList~Asset~
}

Asset  ..>  Dependency : «create»
AssetFileReader  ..>  PEBKAC : «create»
Asseteer  ..>  Dependency : «create»
Asseteer  ..>  PEBKAC : «create»
Asseteer  ..>  Programvara : «create»
Asseteer  ..>  SBOM : «create»
Asseteer  ..>  WeirdFileException : «create»
Programvara  -->  Asset 
ProgramvaraFileReader  -->  AssetFileReader 
ProgramvaraFileReader  ..>  Dependency : «create»
ProgramvaraFileReader  ..>  Programvara : «create»
ProgramvaraFileReader  ..>  WeirdFileException : «create»
SBOM  -->  Asset 
SBOMFileReader  -->  AssetFileReader 
SBOMFileReader  ..>  Dependency : «create»
SBOMFileReader  ..>  SBOM : «create»
SBOMFileReader  ..>  WeirdFileException : «create»
```
