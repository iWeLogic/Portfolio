---
name: module-structure
description: |
  Module layout, dependency rules, Hilt wiring across modules, and Gradle convention plugins for Android projects. Use this skill whenever setting up a new Android project, deciding where a new module or class should live, asking "how should I structure this", creating a new feature module, adding a core submodule, configuring Gradle convention plugins, working with version catalogs, placing Hilt modules or dispatcher qualifiers, or making any decision about project-level architecture. Trigger on phrases like "set up the project", "add a module", "create a feature", "how should I structure", "project structure", "convention plugin", "build-logic", "Hilt module", "where does X live", or "settings.gradle".
---

# Module Structure

## Core Philosophy

- **Feature-layered modularization**: split by feature first, then by layer within each feature.
- **Clean Architecture layers**: `presentation` → `domain` ← `data`. Domain is innermost and depends on nothing but `core:domain`.
- **Code lives in a feature module unless it is needed by more than one feature** — then it moves to the appropriate `core` submodule.
- Features **never depend on each other**. Cross-feature shared data belongs in `core:domain` (domain models) or `core:presentation` (shared UI logic), not in the owning feature. Navigation between features goes through `:core:navigation` — see the `navigation` skill → Conventions.

---

## Module Layout

```
:app                            ← Application, MainActivity
:build-logic                    ← Gradle convention plugins
:core:domain                    ← Shared domain models, repository interfaces, DataError, AppResult, dispatcher qualifiers
:core:data                      ← Shared data logic, Retrofit/OkHttp setup, DispatchersModule, shared Hilt modules
:core:database                  ← Room @Database, entities, DAOs, migrations (only if the DB is shared)
:core:presentation              ← Shared UI utilities (ObserveAsEvents, UiText, DataError.toUiText())
:core:design-system             ← Reusable Compose components, colors, theme, typography
:core:navigation                ← Navigator, EntryProviderInstaller, NavKeys shared across features
:core:testing                   ← Shared test utilities (MainDispatcherExtension) — no fakes; test deps only
:feature:<name>:domain          ← Feature-specific domain models, repository interfaces, use cases, error types
:feature:<name>:data            ← Repository implementations, data source interfaces + implementations, DTOs, mappers, Retrofit services, Hilt @Binds modules
:feature:<name>:presentation    ← ViewModels, screen composables, state/actions/events
```

For standalone, self-contained concerns that involve meaningful complexity (multiple classes, configuration, or a non-trivial API surface), create a dedicated module under `:core` (e.g., `:core:location`, `:core:analytics`). Do not create a separate module for a single class or a trivial utility — that belongs in an existing `core` module instead.

A shared Room database lives in `:core:database`: the `@Database` class, all entities, all DAOs, migrations, and the Hilt module providing the database and DAOs. Feature `data` modules that need DB access depend on `:core:database` directly; features that don't need it remain decoupled. Entities never leak past the `data` layer — map them to domain models in the feature's `data` module.

---

## Dependency Rules

### Feature modules

| Module | May depend on |
|---|---|
| `feature:<name>:presentation` | own `domain`, `core:domain`, `core:presentation`, `core:design-system`, `core:navigation` |
| `feature:<name>:data` | own `domain`, `core:domain`, `core:data`, `core:database` |
| `feature:<name>:domain` | `core:domain` only — never `data` or `presentation` |

### Core modules

| Module | May depend on |
|---|---|
| `core:domain` | nothing (pure Kotlin) |
| `core:data` | `core:domain` |
| `core:database` | nothing |
| `core:presentation` | `core:domain` |
| `core:design-system` | nothing |
| `core:navigation` | nothing |
| `core:testing` | `core:domain`; consumed only as `testImplementation` — its utilities are JUnit5, and instrumented tests run on JUnit4 |

### App

| Module | May depend on |
|---|---|
| `:app` | everything. Hilt collects `@InstallIn` modules (including `@IntoSet` navigation installers) from `:app`'s runtime classpath, transitive deps included. `:app` must depend directly on **every** feature `data` and `presentation` module (nothing else depends on them), on `core:data` (`DispatchersModule`), and on any module whose types it uses (`core:navigation`). `core:database` arrives transitively. Details: `di-hilt` → Application and Entry Points |

**Every** module except `core:database`, `core:design-system`, and `core:navigation` may access `core:domain`.

Domain modules are pure Kotlin/JVM. Allowed non-Kotlin-stdlib dependencies: `kotlinx-coroutines-core` and `javax.inject` (for `@Inject` constructors and `@Qualifier` annotations). No Android, no Hilt/Dagger runtime.

---

## Hilt Across Modules

| What | Where |
|---|---|
| `@HiltAndroidApp` Application, `@AndroidEntryPoint` Activity | `:app` |
| Dispatcher qualifiers (`@IoDispatcher`, `@DefaultDispatcher`, `@MainDispatcher`) | `core:domain` — pure `javax.inject.Qualifier`, so domain use cases can inject dispatchers too |
| `DispatchersModule` (`@Provides` for each dispatcher) | `core:data` |
| Retrofit / OkHttp / Json `@Provides` | `core:data` |
| Database + DAO `@Provides` | `core:database` |
| `@Binds` repository and data source implementations → interfaces | `feature:<name>:data` |
| `@HiltViewModel` classes | `feature:<name>:presentation` |
| Use cases | `feature:<name>:domain` with plain `@Inject constructor` — no Hilt module needed |

Prefer `@Binds` in an `abstract class` module over `@Provides` for interface → implementation bindings.

```kotlin
// feature:notes:data
@Module
@InstallIn(SingletonComponent::class)
abstract class NoteDataModule {
    // Unscoped: no state of its own (Room is the source of truth).
    // Scope it only when it holds a cache or a Mutex.
    @Binds
    abstract fun bindNoteRepository(impl: OfflineFirstNoteRepository): NoteRepository
}
```

---

## Convention Plugins (`:build-logic`)

Define a convention plugin for every non-trivial Gradle config:

| Plugin | Purpose |
|---|---|
| `android-application` | App module config (applicationId, versionCode, etc.) + `compose` + `dagger-hilt` + `core:data` dependency (so `DispatchersModule` is always on the Hilt classpath) + `testInstrumentationRunner` = `HiltTestRunner` (other modules keep `AndroidJUnitRunner`) |
| `android-library` | Base Android library config (compileSdk, minSdk, JVM target, `junit5`) |
| `android-feature` | `android-library` + `compose` + `dagger-hilt` + `kotlinx-serialization` + shared presentation deps (`core:presentation`, `core:design-system`, `core:navigation`, lifecycle, Navigation 3, Hilt Compose integration) |
| `domain-module` | Pure Kotlin/JVM — no Android deps; adds `kotlinx-coroutines-core`, `javax.inject`, `junit5`, `java-test-fixtures` (fakes of the module's interfaces live in `src/testFixtures`) |
| `compose` | Compose compiler + BOM + stability configuration file (`compose_compiler_config.conf`, see `compose` → Stability & Recomposition) |
| `dagger-hilt` | Hilt Gradle plugin, Hilt runtime, Dagger compiler via KSP |
| `room` | Room + KSP config, schema export directory |
| `kotlinx-serialization` | KotlinX Serialization plugin + dep |
| `junit5` | `de.mannodermaus.android-junit5` for Android modules (plain `useJUnitPlatform()` for JVM modules), JUnit5, Turbine, AssertK, `kotlinx-coroutines-test` |

Module-to-plugin mapping:

| Module type | Plugins |
|---|---|
| `:app` | `android-application` |
| `feature:<name>:presentation` | `android-feature` |
| `feature:<name>:data` | `android-library`, `dagger-hilt` (+ `kotlinx-serialization` for DTOs, + `room` if it owns a DB) |
| `feature:<name>:domain`, `core:domain` | `domain-module` |
| `core:data` | `android-library`, `dagger-hilt`, `kotlinx-serialization`; `buildFeatures { buildConfig = true }` for `BASE_URL` |
| `core:database` | `android-library`, `dagger-hilt`, `room` |
| `core:presentation` | `android-library`, `compose` |
| `core:design-system` | `android-library`, `compose` |
| `core:navigation` | `android-library`, `compose`, `kotlinx-serialization` + Navigation 3 runtime (no Hilt — `Navigator` is created in `MainActivity`, not by Hilt) |
| `core:testing` | `domain-module` + JUnit5 API, `kotlinx-coroutines-test`, Turbine, AssertK as **main** (`api`) dependencies — consumers use them through `MainDispatcherExtension` and shared test utilities |

Use **version catalogs** (`libs.versions.toml`) for all dependency and version management. No hardcoded versions in build files.

---

## Key Libraries

| Concern | Library |
|---|---|
| DI | Dagger Hilt |
| Networking | Retrofit + OkHttp |
| Local DB | Room |
| Preferences | DataStore |
| Navigation | Jetpack Navigation 3 |
| Serialization | KotlinX Serialization |
| Image loading | Coil |
| Async | Coroutines + Flow |
| Background tasks | WorkManager (with Hilt `@HiltWorker`) |
| Secrets | `local.properties` + `BuildConfig` |
| Unit testing | JUnit5 (via android-junit5), Turbine, AssertK, `kotlinx-coroutines-test` |
| UI testing | `ComposeTestRule` (instrumented tests run on JUnit4 runner) |

---

## Checklist: Adding a New Feature Module

- [ ] Create `:feature:<name>:domain`, `:feature:<name>:data`, `:feature:<name>:presentation`
- [ ] Register all three in `settings.gradle.kts`
- [ ] Apply convention plugins (`domain-module`, `android-library` + `dagger-hilt`, `android-feature`)
- [ ] Add a `@Binds` Hilt module in `feature:<name>:data` for each repository and data source interface
- [ ] Add the feature's `data` and `presentation` modules as dependencies of `:app`
- [ ] Put fakes of the feature's domain interfaces in `feature:<name>:domain/src/testFixtures`; consume them via `testImplementation(testFixtures(...))` in `data`/`presentation` and `androidTestImplementation(testFixtures(...))` in `:app`
- [ ] Verify no cross-feature dependencies are introduced
- [ ] If logic is shared across 2+ features, extract to the appropriate `core` submodule
