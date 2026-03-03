# Interview noter — Sall Whisky Destillery

## Kontekst og problemstilling

**Hvad løser koden?**
- Et administrationsystem for et whisky-destilleri — tracking af korn, destilleringer, fadlagring og flaskning.
- Eksisterende JavaFX-desktopapp skulle moderniseres til en REST API + Angular webapplikation med henblik på skalerbarhed og flerbruger-support.

**Hvem arbejdede du med?**
- Primært soloprojekt fra studie (JavaFX-versionen), rewritten som ny stack for at demonstrere Spring Boot + Angular.

---

## Tekniske valg og overvejelser

### Det gode

**Rich domain model frem for anæmisk**
Entiteterne indeholder rigtig domænelogik — de er ikke bare databeholdere med getters/setters:
- `Destillat.erKlar()` — den centrale forretningsregel: 3-årig modning. Logikken bor på selve objektet, der kender sin egen `startDato`.
- `WhiskyProdukt.whiskyType()` — klassificerer automatisk om produktet er *Cask Strength*, *Single Cask* eller *Single Malt* ud fra sammensætningen.
- `WhiskyProdukt.udregnAlkoholProcent()` — vægtet gennemsnit, udregnet ved hver ændring (tapning, vandtilsætning).

Det er et bevidst valg frem for at putte al logik i services (anæmisk domænemodel). Fordelen er at entiteter er selvvaliderende og nemmere at enhedsteste isoleret.

**DTO-lag som API-kontrakt**
Java records som DTOs (`FadResponse`, `OpretFadRequest` osv.). Records er immutable by default, kan ikke ved et uheld modificeres efter konstruktion, og de er compilerens garanti for at API-kontrakten er komplet. Adskillelse af intern entitet og eksponeret DTO beskytter mod at interne JPA-relationer lækker ud (cirkulære referencer, lazy loading exceptions).

**Dependency Injection frem for statisk kode**
Den gamle JavaFX-version brugte `Controller` som en `static abstract class` — et anti-pattern. Det er:
- Ikke testbart (kan ikke mockes)
- Binder GUI til storage-implementationen uden mulighed for at skifte ud
- Thread-unsafe (delt global tilstand)

Spring-versionen bruger constructor injection (`@RequiredArgsConstructor`) overalt. Alt kan mockes og testes isoleret.

**Validering i backend, ikke kun frontend**
Et eksempel der kom op under udviklingen: Angular-modalen viser kun fade der er klar til tapning (`getFadeKlar()`), men `POST /api/whisky/{id}/tap` validerede ikke selve `erKlar()`-reglen. Frontenden er convenience — backenden er sandheden. Reglen sidder nu i `WhiskyService.tapFad()`.

**ModningsHistorik som audit trail**
Hver gang en destillat flyttes fra ét fad til et andet, lukkes den eksisterende historikpost (`slutDato = now()`) og en ny åbnes. Det giver et komplet sporingsforløb — vigtigt i en reguleret branche.

---

### Det kunne gøres bedre (kritisk refleksion)

**`WhiskyService.opretFlasker()` blander to ansvarsområder**
```java
whiskyProdukt.getNavn() + " – flaske " + (startNummer + i) + " af " + ...
```
Etiket-teksten bygges med stringkonkatenation direkte i servicemetoden. Det er en præsentationsbeslutning i forretningslogikken. En `EtiketBuilder`-klasse (eller i det mindste en privat hjælpemetode) ville overholde Single Responsibility bedre — og gøre det nemt at ændre etiketten uden at røre selve flaskningslogikken. Det er præcis det samme problem som i den gamle JavaFX-version, bare rykket fra entitet til service.

**`WhiskyProdukt.whiskyType()` er skrøbelig**
```java
if (fadTapninger.size() == 1) {
    if (destillat.getModningsHistorik().size() == 1) {
        return literVandTilfojet == 0 ? "Cask Strength" : "Single Cask";
    }
}
return "Single Malt";
```
Returner hardcoded strings. Ville have gavn af en `enum WhiskyType` — så compileren fanger typos, og man kan bruge den i switch-udtryk. Derudover er klassifikationslogikken lidt "mønstermatchet" — en Strategy eller et table-driven opslag ville gøre det nemmere at tilføje nye typer (fx "Blended") uden at ændre entiteten (Open/Closed Principle).

**`FadService.opretFad()` har en race condition**
```java
long count = fadRepository.count();
fad.setFadNummer("F-%03d".formatted(count + 1));
```
Hvis to requests rammer serveren simultant, kan de begge get count=9 og begge forsøge at oprette F-010. Den gamle JavaFX-version brugte en statisk tæller med samme problem. En bedre løsning er en databasesekvens (`@GeneratedValue` på fadNummer) eller et UUID-baseret alternativt nummer.

**`toResponse()`-metoder burde ikke ligge i services**
`FadService.toResponse()` er 30+ linjer med manuel mapping. Det er ikke FadService's ansvar at vide, hvordan en Fad mappes til JSON. I et større projekt ville man bruge MapStruct-annotationer (en mapper-interface, og MapStruct genererer implementationen ved compile-tid). Det ville reducere services til ren orkestrering.

**Ingen paginering**
`getAlleFade()` returnerer alle fade på én gang. Med 10 fade er det fint. Med 10.000 fade er det ikke. Spring Data JPA har `Pageable` støtte out of the box — det burde have været tænkt ind fra starten.

**Validering er inkonsistent**
`@Positive` og `@NotBlank` er på entiteterne, men `OpretFadRequest` mangler `@Min(1)` på `literKapacitet` og `@Past` på `fraAar`. Request-DTO'erne er API-grænsen — validering bør sidde der, og ikke kun på JPA-lagssiden.

---

## Kvalitet og vedligeholdelse

**Test**
`FadServiceTest` med Mockito tester de vigtigste forretningsregler isoleret (modning, ABV-beregning, whisky-klassificering) uden at starte Spring-container. Det er hurtigt og præcist. Men der mangler integrationstests med `@SpringBootTest` + H2, der tester at fx `paafyldFad` faktisk skriver det rigtige til databasen.

**Læsbarhed**
JavaFX-versionen: metodenavne på engelsk i en dansk kodebase, statiske metoder uden kontekst, ingen DTO-lag. Spring-versionen er mere konsistent: dansk domænenavngivning gennemgående, tydelig lagdeling, Javadoc på de ikke-trivielle metoder.

---

## Refleksion — alternativer du overvejede

**Anæmisk vs. rich domain model**
Man kunne have lagt al logik i services (som mange Spring-projekter gør). Det er nemmere at starte med, men resulterer i services der ved alt om domænet, og entiteter der er glorificerede dataholdere. Her valgte vi rich model — logikken bor tæt på dataen.

**H2 vs. rigtig database**
H2 in-memory er god til demo og test, men data forsvinder ved restart. I produktion ville man bruge PostgreSQL med Flyway-migrationer, så skemaændringer er versionsstyrede.

**Java serialization (`.srl`) vs. JPA**
Den gamle tilgang med Java-serialisering til en fil: ethvert navneskift på en klasse bryder deserialisering, statiske tællere skal manuelt genoprettet ved opstart, ingen query-muligheder. JPA + H2 løser alle tre.

**Spring Security / auth**
HTTP Basic med hardcoded `admin/admin` er kun egnet til demo. Næste skridt ville være JWT-tokens — stateless, skalerbart, ingen sessionhåndtering på serveren.

---

## Hurtigopsummering

| Emne | Pointe |
|---|---|
| Rich domain model | `erKlar()`, `whiskyType()`, `udregnAlkoholProcent()` lever på entiteterne, ikke i services |
| DTOs som records | Immutabilitet, klar API-kontrakt, beskytter mod JPA-lækage |
| Statisk Controller (gammel) | Ikke testbar, ikke DI, procedurelt OOP-anti-pattern |
| Etiket-logik i service | SRP-brud — præsentation og forretning blandet. Burde været en `EtiketBuilder` |
| `whiskyType()` strings | Brug `enum`, og Strategy for OCP |
| Race condition på fadNummer | `count+1` er ikke atomisk — brug DB-sekvens |
| Validering frontend vs. backend | Frontend er convenience; reglen skal håndhæves i service. Opdagedes under udvikling og rettedes |
| Ingen paginering | Teknisk gæld fra dag ét |
| `toResponse()` i service | Mapping-ansvar hører til en dedikeret mapper (MapStruct) |
