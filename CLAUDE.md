# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Architecture
Clean Architecture, single-activity, Jetpack Compose, Hilt, Navigation 3, MVI in the presentation layer.

Target modules (details and dependency rules in the `module-structure` skill):
- `:app`, `:build-logic`
- `:core:domain`, `:core:data`, `:core:database`, `:core:presentation`, `:core:design-system`, `:core:navigation`, `:core:testing`
- `:feature:<name>:domain`, `:feature:<name>:data`, `:feature:<name>:presentation`

Dependency rule: presentation → domain ← data. Domain is pure Kotlin (no Android dependencies). Features never depend on each other.

Currently only `:app` exists; new code goes into the target modules above as they are created.

## Project skills (`.claude/skills/`)
Follow them for any code in their area — they override generic Android conventions:
- `module-structure` — where a module or class lives, dependency rules, convention plugins
- `presentation-mvi` — ViewModels, State/Action/Event, Root/Screen, navigation wiring (Navigator + `@IntoSet` `EntryProviderInstaller`)
- `navigation` — Navigation 3 recipes (Google)
- `compose` — composables, previews, stability, text input
- `data-layer`, `error-handling` — repositories, data sources, `AppResult`/`DataError`
- `di-hilt` — Hilt modules, scopes, qualifiers
- `testing` — JUnit5, Turbine, AssertK, fakes, Compose UI tests

## Commands

```bash
./gradlew assembleDebug                 # build debug APK
./gradlew installDebug                  # build + install on connected device/emulator
./gradlew test                          # JVM unit tests (src/test; JUnit5 per the `testing` skill — the template ExampleUnitTest is still JUnit4)
./gradlew connectedAndroidTest          # instrumented tests (app/src/androidTest), needs device/emulator
./gradlew lint                          # Android lint

# single test class / method
./gradlew :app:testDebugUnitTest --tests "com.iwelogic.ExampleUnitTest"
./gradlew :app:testDebugUnitTest --tests "com.iwelogic.ExampleUnitTest.addition_isCorrect"
./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.iwelogic.ExampleInstrumentedTest
```

## Build setup notes

- **AGP 9.x with built-in Kotlin**: there is no separate `kotlin-android` plugin. Don't add one. Currently only `com.android.application` and `org.jetbrains.kotlin.plugin.compose` are applied; Hilt, KSP, KotlinX Serialization, Room and android-junit5 are added through `:build-logic` convention plugins (see `module-structure`).
- Gradle 9.5 wrapper; daemon JVM toolchain is Java 21 (auto-provisioned via foojay, see `gradle/gradle-daemon-jvm.properties`). App bytecode targets Java 11.
- All dependency/plugin versions live in the version catalog `gradle/libs.versions.toml`; reference them as `libs.*` in build scripts. Compose library versions come from the Compose BOM, so Compose artifacts in the catalog have no version.
- Repositories are declared centrally in `settings.gradle.kts` with `FAIL_ON_PROJECT_REPOS` — module build files must not declare repositories.
- Configuration cache is enabled (`gradle.properties`); build logic must be configuration-cache compatible.
- `minSdk 28`, `targetSdk 36`, `compileSdk` 36.1 (new `compileSdk { version = release(36) { minorApiLevel = 1 } }` DSL).
- Release build uses the new `optimization { enable = false }` DSL (R8 off). Keep rules live in `app/src/main/keepRules/rules.keep`, not `proguard-rules.pro`.

## Code layout

- Package root: `com.iwelogic` (`app/src/main/java/...`).
- `ui/theme/` holds `PortfolioTheme` (Material 3, dynamic color on Android 12+ with purple/pink fallback schemes), `Color.kt`, `Type.kt`. It moves to `:core:design-system` once that module exists. Wrap all screens, `@Preview`s and Compose UI tests in `PortfolioTheme`. The skills call it `AppTheme` — in this project `AppTheme` means `PortfolioTheme`.
- `MainActivity` uses `enableEdgeToEdge()` + `PortfolioTheme` + `NavDisplay`. Each Screen composable owns its `Scaffold` (top bar, snackbar host) and applies its `innerPadding` to its content. An app-level `Scaffold` is added only for shared chrome (bottom navigation), with `consumeWindowInsets` on the padding it passes down (see `presentation-mvi` → Registering entries). The template `MainActivity` still has a `Scaffold`; it is replaced when navigation is added.
