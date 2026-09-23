---
name: navigation
description: Learn how to install Jetpack Navigation 3, and how to
  implement features and patterns such as deep links, multiple backstacks, scenes
  (dialogs, bottom sheets, list-detail, two-pane, supporting pane), conditional navigation
  (such as logged-in navigation versus anonymous), returning results from flows, integration
  with Hilt, ViewModel, Kotlin, and view interoperability.
license: Code samples Apache-2.0; documentation text CC-BY-4.0 (https://developer.android.com/license). Adapted for these skills — see Conventions.
metadata:
  author: Google LLC
  last-updated: '2026-09-10'
  keywords:
  - recipe
  - Android
  - Navigation 3
  - Compose
  - guide
  - dependencies
  - NavKey
  - NavHost
  - NavDisplay
  - BottomSheet
  - list-detail
  - scenes
  - two-pane
  - supporting pane
  - multiple backstacks
  - dialog
  - Hilt
  - ViewModel
  - View interop.
---

*** ** * ** ***

## Conventions

The recipes below are Google's reference code. In this project they are adapted as follows — these rules override the recipes where they differ:

- **Modular navigation** follows the [Modularized navigation code (Hilt)](references/android/guide/navigation/navigation/recipes/modular-hilt.md) recipe with two deviations:
  - **No per-feature `api` / `impl` modules.** The recipe's `common` module is `:core:navigation` (`Navigator`, `EntryProviderInstaller`), and it also plays the role of the `api` modules: keys of screens reachable from other features (or used as the start destination) live there. Keys of screens reachable only within a feature stay in `feature:<name>:presentation`. Features are `domain` / `data` / `presentation` (see `module-structure`).
  - **`Navigator` is not a Hilt binding.** `MainActivity` creates it around `rememberNavBackStack` (see [Saveable back stack](references/android/guide/navigation/navigation/recipes/basicsaveable.md)), so the back stack survives configuration changes and process death; installers receive it as a parameter: `EntryProviderInstaller = EntryProviderScope<NavKey>.(Navigator) -> Unit`. There is no `AppNavigationModule`.
- **NavKey fields are IDs and primitives only** — never domain models. The ViewModel loads data by ID from a repository. This keeps keys small for saved state and keeps `:core:navigation` free of any dependency on `core:domain`.
- **Navigation arguments** reach the ViewModel through Hilt assisted injection (`hiltViewModel` + `creationCallback`), never through `SavedStateHandle`.
- The full wiring (installers, `NavDisplay`, decorators, Root composables) is in `presentation-mvi` → Registering entries.

## Developer documentation

- *[Navigation 3](references/android/guide/navigation/navigation/index.md)*. Search documentation for more information on basics, saving and managing navigation state, modularizing navigation code, creating custom layouts using Scenes, animating between destinations, or applying logic or wrappers to destinations.

## Recipes

Code examples showcasing common patterns.

### Basic API usage

- *[Basic](references/android/guide/navigation/navigation/recipes/basic.md)*: Shows most basic API usage.
- *[Saveable back stack](references/android/guide/navigation/navigation/recipes/basicsaveable.md)*: Shows basic API usage with a persistent back stack.
- *[Entry provider DSL](references/android/guide/navigation/navigation/recipes/basicdsl.md)*: Shows basic API usage using the entryProvider DSL.

### Common UI

- *[Common UI](references/android/guide/navigation/navigation/recipes/common-ui.md)*: Demonstrates how to implement a common navigation UI pattern with a bottom navigation bar and multiple back stacks, where each tab in the navigation bar has its own navigation history.

### Deep links

- *[Static URI](references/android/guide/navigation/navigation/recipes/deeplinks-staticuri.md)*: Shows how to handle simple static URI deep links.
- *[URI with Arguments](https://developer.android.com/guide/navigation/navigation-3/recipes/deeplinks-uriarguments)*: Shows how to parse path and query arguments from a deep link. *Online only — no local copy in `references/`; fetch the page when needed.*
- *[Synthetic BackStack](references/android/guide/navigation/navigation/recipes/deeplinks-syntheticbackstack.md)*: Shows how to handle deep links with a synthetic back stack.
- *[Custom Matcher](references/android/guide/navigation/navigation/recipes/deeplinks-custommatcher.md)*: Shows how to implement custom deep link matching logic.

### Scenes

#### Use built-in Scenes

- *[Dialog](references/android/guide/navigation/navigation/recipes/dialog.md)*: Shows how to create a Dialog.

#### Create custom Scenes

- *[BottomSheet](references/android/guide/navigation/navigation/recipes/bottomsheet.md)*: Shows how to create a BottomSheet destination.
- *[List-Detail Scene](references/android/guide/navigation/navigation/recipes/scenes-listdetail.md)*: Demonstrates how to implement adaptive list-detail layouts using the Navigation 3 Scenes API.
- *[Two pane Scene](references/android/guide/navigation/navigation/recipes/scenes-twopane.md)*: Demonstrates how to implement adaptive two-pane layouts using the Navigation 3 Scenes API.

### Material Adaptive

- *[Material List-Detail](references/android/guide/navigation/navigation/recipes/material-listdetail.md)*: Demonstrates how to implement an adaptive list-detail layout using Material 3 Adaptive.
- *[Material Supporting Pane](references/android/guide/navigation/navigation/recipes/material-supportingpane.md)*: Demonstrates how to implement an adaptive supporting pane layout using Material 3 Adaptive.

### Animations

- *[Animations](references/android/guide/navigation/navigation/recipes/animations.md)*: Shows how to override the default animations for all destinations and a single destination.
- *[Conditional Transitions](references/android/guide/navigation/navigation/recipes/conditional-transitions.md)*: Shows how to implement conditional transition animations.

### Common back stack behavior

- *[Multiple back stacks](references/android/guide/navigation/navigation/recipes/multiple-backstacks.md)*: Shows how to create multiple top level routes, each with its own back stack. Top level routes are displayed in a navigation bar allowing users to switch between them. State is retained for each top level route, and the navigation state persists config changes and process death.

### Conditional navigation

- *[Conditional navigation](references/android/guide/navigation/navigation/recipes/conditional.md)*: Switch to a different navigation flow when a condition is met. For example, for authentication or first-time user onboarding.

### Lifecycle

- *[Lifecycle Owner](references/android/guide/navigation/navigation/recipes/lifecycle-owner.md)*: Shows how to use and observe Lifecycle in Navigation 3.

### Architecture

- *[Modularized navigation code (Hilt)](references/android/guide/navigation/navigation/recipes/modular-hilt.md)*: Demonstrates how to decouple navigation code into separate modules using Hilt or Dagger for DI.

### Working with ViewModel

#### Passing navigation arguments

- *[Basic ViewModel](references/android/guide/navigation/navigation/recipes/passingarguments.md)* : Navigation arguments are passed to a `ViewModel` via Hilt assisted injection (`hiltViewModel` + `creationCallback`) or a plain `viewModel()` factory. This project uses the Hilt variant (see `presentation-mvi`).

### Returning results

- *[Returning Results as Events](references/android/guide/navigation/navigation/recipes/results-event.md)* : Returning results as events to content in another `NavEntry`
- *[Returning Results as State](references/android/guide/navigation/navigation/recipes/results-state.md)* : Returning results as state stored in a `CompositionLocal`
