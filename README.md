# Sall Whisky Distillery Application

A full-stack application for managing the Sall Whisky Distillery — tracking grain batches, distillation runs, barrel fills, warehouse storage, and whisky bottling.

The project exists in two parallel implementations:
- **JavaFX desktop app** — the original implementation
- **Spring Boot + Angular SPA** — a modern rewrite demonstrating full-stack web development

---

## Architecture Overview

```
┌─────────────────────────────────────────────────────┐
│              JavaFX Desktop App (src/)               │
│  LoginPane → StartVindue → [4 tabs]                 │
│       ↓ calls                                        │
│  Controller (static facade)                          │
│       ↓ calls                                        │
│  ListStorage (.srl serialization)                    │
└─────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────┐
│         Spring Boot + Angular (backend/ + frontend/) │
│  Angular 17 SPA ──HTTP Basic──> Spring Boot 3.2 REST │
│                                    ↓ JPA             │
│                               H2 in-memory DB        │
└─────────────────────────────────────────────────────┘
```

### Domain Model

```
Korn ──> Destillering ──> Påfyldning ──> Destillat ──> Fad ──> Hylde ──> Reol ──> Lager
                                                         │
                                                    FadTapning ──> WhiskyProdukt ──> WhiskyFlaske
```

---

## Stack 1 — JavaFX Desktop App

### Requirements
- Java 19 (liberica-19)
- IntelliJ IDEA (no Maven/Gradle — plain IDE project)
- JavaFX on the module path/classpath

### Running
Open in IntelliJ and run `src/gui/App.java` → `App.main()`.

**Login**: `admin` / `admin`

### Architecture
Three-layer: **GUI → Controller → Storage**

| Layer | Location | Notes |
|-------|----------|-------|
| GUI | `src/gui/` | JavaFX panes and modal dialogs |
| Controller | `src/application/controller/Controller.java` | Static abstract class — all methods are static |
| Storage | `src/storage/ListStorage.java` | ArrayList-backed, Java object serialization to `storage.srl` |

### Key Behaviour
- `storage.srl` is written to the working directory on shutdown. Delete it to reset to sample data.
- Static counters (`Fad.antalFade`, `Destillering.antalDestilleringer`) are restored from the snapshot on load.
- A `Destillat` is ready for bottling after **3 years** — checked via `Destillat.destillatKlar()`.

### Tests
`Test/application/models/ModelsTest.java` — run via IntelliJ's JUnit 5 runner.

---

## Stack 2 — Spring Boot + Angular

### Requirements
- Java 17+, Maven
- Node.js 18+, npm

### Running the Backend

```bash
cd backend
./mvnw spring-boot:run
```

- API base: `http://localhost:8080/api`
- H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:sallwhisky`)
- Auth: HTTP Basic — `admin` / `admin`

The database is seeded automatically on startup by `DataInitializer`.

### Running the Frontend

```bash
cd frontend
npm install
npm start
```

Opens at `http://localhost:4200`.

### Running Backend Tests

```bash
cd backend
./mvnw test
```

---

## API Reference

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/fade` | All barrels |
| `GET` | `/api/fade/tomme` | Empty barrels |
| `GET` | `/api/fade/fyldte` | Filled barrels |
| `GET` | `/api/fade/klar` | Barrels ready for bottling (≥3 years) |
| `POST` | `/api/fade` | Create barrel |
| `DELETE` | `/api/fade/{id}` | Delete empty barrel |
| `POST` | `/api/fade/{id}/destillat` | Fill barrel with distillate |
| `PUT` | `/api/fade/{id}/flyt` | Move barrel to shelf |
| `POST` | `/api/fade/{fra}/omhaeld/{til}` | Re-barrel distillate |
| `GET/POST` | `/api/destilleringer` | List / create distillation run |
| `GET/POST` | `/api/korn` | List / create grain type |
| `GET/POST` | `/api/lagre` | List / create warehouse |
| `POST` | `/api/lagre/{id}/reoler` | Add racks to warehouse |
| `GET/POST` | `/api/whisky` | List / create whisky product |
| `POST` | `/api/whisky/{id}/tap` | Tap a barrel into product |
| `POST` | `/api/whisky/{id}/vand` | Add water (body: `{ "liter": N }`) |
| `POST` | `/api/whisky/{id}/flasker` | Bottle the product |

---

## Project Structure

```
Sall-Whisky-Destillery-Application/
├── src/                          # JavaFX desktop app
│   ├── application/
│   │   ├── controller/           # Static Controller facade
│   │   └── models/               # Domain models (Serializable)
│   ├── gui/                      # JavaFX panes and dialogs
│   └── storage/                  # ListStorage + Storage interface
├── backend/                      # Spring Boot 3.2
│   └── src/main/java/dk/sallwhisky/
│       ├── api/controller/       # REST controllers
│       ├── api/dto/              # Request/Response records
│       ├── domain/entity/        # JPA entities
│       ├── domain/repository/    # Spring Data repositories
│       ├── domain/service/       # Business logic
│       └── config/               # Security, CORS, DataInitializer
├── frontend/                     # Angular 17 SPA
│   └── src/app/
│       ├── core/                 # Auth interceptor, models, HTTP service
│       └── features/             # fade, destillering, lagerstyring, whisky
├── Test/                         # JavaFX JUnit 5 tests
└── diagrams/                     # Architecture and domain SVG diagrams
```

---

## Key Design Decisions

- **Danish naming**: The domain follows Danish terminology throughout (`Fad`, `Påfyldning`, `Reol`, `Destillering`, etc.) matching the real-world domain.
- **Weighted ABV**: When filling a barrel from multiple distillations, ABV is the volume-weighted average.
- **Whisky types**: `WhiskyProdukt.whiskyType()` returns `"Cask Strength"`, `"Single Cask"`, or `"Single Malt"` based on composition.
- **Re-barreling**: Moving a distillate to a new barrel (`omhæld`) records a new `ModningsHistorik` entry, preserving the full aging history.
- **Angular state**: All state is managed with Angular 17 signals (`signal()`, `computed()`) — no NgRx.
- **Modals**: Bootstrap 5 native modals via CDN (`(window as any).bootstrap.Modal`) — no ng-bootstrap dependency.
