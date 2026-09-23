---
name: compose
description: |
  Jetpack Compose UI patterns for Android - stability configuration, recomposition, side effects, lazy lists, animations with deferred reads, Modifier.Node, previews, accessibility, TextFieldState, composable API conventions, and design system components. Use this skill whenever writing or reviewing composables, optimizing recomposition, adding animations, creating previews, writing custom modifiers, building text input, structuring a design system, or making any Compose UI decision beyond the MVI/ViewModel layer. Trigger on phrases like "composable", "recomposition", "LaunchedEffect", "DisposableEffect", "Modifier", "Modifier.Node", "LazyColumn", "preview", "animation", "design system", "stability", "@Stable", "@Immutable", "contentDescription", "graphicsLayer", "slot API", "TextField", "TextFieldState", or "Compose performance".
---

# Compose UI Patterns

For ViewModel, State, Action, Event, and the Root/Screen split, see the `presentation-mvi` skill.

## Core Principle

The UI is dumb. Composables render state and forward user actions — nothing more. All screen state lives in the ViewModel. All logic lives in the ViewModel, domain, or data layer. Compose code contains zero business logic, zero data transformation, and minimal side effects.

---

## Stability & Recomposition

Strong skipping mode is enabled by default (Kotlin 2.0+ Compose compiler). Every restartable composable is skippable: stable parameters are compared with `equals()`, unstable ones with instance equality (`===`).

### Stability configuration file (preferred)

Instead of annotating classes one by one, declare commonly used types as stable in a config file applied by the `compose` convention plugin:

```
// compose_compiler_config.conf (project root)
kotlin.collections.*
java.time.*
```

```kotlin
// build-logic, compose convention plugin
composeCompiler {
    stabilityConfigurationFiles.add(
        rootProject.layout.projectDirectory.file("compose_compiler_config.conf")
    )
}
```

With this in place, `List<NoteUi>` parameters are compared with `equals()`, and state classes like `NoteListState` need no annotation. Only put types in this file that are never mutated after creation in this codebase.

### When to annotate

- Classes from modules **without** the Compose compiler (e.g. pure Kotlin `domain` modules) are always inferred unstable. Don't pass domain models to composables — map them to `*Ui` models in `presentation` (see `presentation-mvi`).
- If a UI model or state class still contains a type the compiler considers unstable (an interface, an abstract type, a third-party class), annotate it with `@Immutable` when all its properties are `val` and never change after construction, otherwise `@Stable`.

```kotlin
// No annotation needed: List is covered by the stability config above,
// and UiText is annotated @Immutable in core:presentation (see `presentation-mvi`)
data class NoteListState(
    val notes: List<NoteUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null
)

// Contains an interface → annotate
@Immutable
data class ChartUi(
    val points: List<Float>,
    val formatter: ValueFormatter
)
```

Use the Compose compiler reports (`reportsDestination` / `metricsDestination`) to verify stability before adding annotations — don't annotate on guesswork.

---

## State Ownership

All screen state lives in the ViewModel and is surfaced via `collectAsStateWithLifecycle()`. Do not use `remember` / `rememberSaveable` for screen state.

Exceptions — Compose-owned UI state:
- `LazyListState`, `ScrollState`, `PagerState`, `SheetState`, `DrawerState`, `SnackbarHostState`, and similar `remember*State` holders.
- Purely visual, ephemeral state local to one component (e.g. whether a tooltip is shown, a design-system component's internal expanded flag).
- `TextFieldState` — see [Text Input](#text-input).

```kotlin
val lazyListState = rememberLazyListState()

// Reacting to Compose-owned state
val showScrollToTop by remember {
    derivedStateOf { lazyListState.firstVisibleItemIndex > 5 }
}
```

Use `derivedStateOf` only when Compose-owned state drives a derived value. If the derivation can happen in the ViewModel, it should.

---

## Side Effects

Avoid side effects when possible. If something can be handled by the ViewModel through an Action, do that instead.

### Lifecycle

Prefer the built-in lifecycle effects from `lifecycle-runtime-compose` over hand-written observers:

```kotlin
LifecycleStartEffect(Unit) {
    onAction(ScreenAction.OnStart)
    onStopOrDispose { onAction(ScreenAction.OnStop) }
}

LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
    onAction(ScreenAction.OnResume)
}
```

### Custom effects

When a side effect is truly necessary and has no built-in equivalent, extract it into a dedicated composable to keep the Screen composable clean. Wrap callbacks with `rememberUpdatedState` so a long-lived effect always calls the latest lambda without restarting:

```kotlin
@Composable
fun ObserveConnectivity(onChanged: (Boolean) -> Unit) {
    val currentOnChanged by rememberUpdatedState(onChanged)
    val context = LocalContext.current
    DisposableEffect(context) {
        val callback = /* register listener, call currentOnChanged(...) */
        onDispose { /* unregister */ }
    }
}
```

Use `LocalLifecycleOwner` from `androidx.lifecycle.compose`, not the deprecated `androidx.compose.ui.platform` one.

`LaunchedEffect` is acceptable when genuinely needed, but question whether the work belongs in the ViewModel first. Do not create custom `CompositionLocal`s. Library-provided ones (e.g. Navigation 3's `LocalResultEventBus` from the `navigation` skill's result recipes) are fine.

---

## Lazy Layouts

Add `key` when there is an obvious unique identifier. Add `contentType` when the list mixes different item layouts, so Compose can reuse compositions between items of the same type:

```kotlin
LazyColumn {
    items(
        items = state.notes,
        key = { it.id },
        contentType = { "note" }
    ) { note ->
        NoteItem(note = note, onClick = { onAction(NoteListAction.OnNoteClick(note.id)) })
    }
}
```

Don't force a `key` if it's unclear which property is unique — a non-unique key crashes at runtime.

---

## Animations

Avoid reading animated values during composition. Read them in the layout or draw phase instead:

- **`graphicsLayer { }`** — alpha, scale, rotation, translation
- **`offset { }`** (lambda version) — position changes
- **`drawBehind { }` / `Canvas`** — custom drawing that animates

```kotlin
val alpha by animateFloatAsState(if (state.isVisible) 1f else 0f)

// Good — `alpha` is read inside the graphicsLayer lambda (draw phase), no recomposition per frame
Box(modifier = Modifier.graphicsLayer { this.alpha = alpha })

// Bad — `alpha` is read during composition, so every frame recomposes
Box(modifier = Modifier.alpha(alpha))
```

The difference is **where the state is read**, not which modifier renders it.

**Deferred state reads:** When a parameter drives an animation, accept it as a lambda so the read happens later:

```kotlin
// Good — deferred read, layout phase only
fun Modifier.animatedOffset(offsetProvider: () -> IntOffset): Modifier =
    offset { offsetProvider() }

// Bad — value is read in composition by the caller
fun Modifier.animatedOffset(offset: IntOffset): Modifier =
    offset { offset }
```

---

## Modifier Extensions

Do not make modifier extensions `@Composable`, and do not use `Modifier.composed { }` — it is discouraged for performance.

- **Simple** — chain existing modifiers in a plain extension:

```kotlin
fun Modifier.roundedBackground(color: Color, radius: Dp): Modifier =
    background(color, RoundedCornerShape(radius))
```

- **Stateful, animated, or custom drawing** — implement `Modifier.Node`:

```kotlin
fun Modifier.dimmed(fraction: Float): Modifier = this then DimmedElement(fraction)

private data class DimmedElement(val fraction: Float) : ModifierNodeElement<DimmedNode>() {
    override fun create() = DimmedNode(fraction)
    override fun update(node: DimmedNode) {
        node.fraction = fraction
    }
}

private class DimmedNode(var fraction: Float) : Modifier.Node(), DrawModifierNode {
    override fun ContentDrawScope.draw() {
        drawContent()
        drawRect(Color.Black.copy(alpha = fraction))
    }
}
```

---

## Composable API Conventions

Parameter order for every public composable:
1. Required parameters (state, data, callbacks without defaults)
2. `modifier: Modifier = Modifier` — the first optional parameter
3. Other optional parameters
4. Trailing `@Composable` content lambda, if any

Apply the `modifier` parameter to the **root** element only, and never reuse it on children.

---

## Design System & Slot APIs

The design system lives in `:core:design-system` and contains reusable components, colors, theme (`AppTheme`), and typography.

`AppTheme` in these skills stands for the project's theme composable. The project's CLAUDE.md names the real one (e.g. `<ProjectName>Theme`); use that name in code.

Use slot APIs (`@Composable` lambdas) primarily for design system components that need flexible content areas:

```kotlin
@Composable
fun AppCard(
    header: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = modifier) {
        header()
        content()
    }
}
```

Feature-level composables prefer typed parameters over slots for clarity.

---

## Previews

Every Screen composable has at least one meaningful preview with a realistic state. Use `@PreviewLightDark` to check both themes:

```kotlin
@PreviewLightDark
@Composable
private fun NoteListScreenPreview() {
    AppTheme {
        NoteListScreen(
            state = NoteListState(
                notes = listOf(
                    NoteUi(id = "1", title = "Meeting notes", formattedDate = "Mar 15"),
                    NoteUi(id = "2", title = "Shopping list", formattedDate = "Mar 14")
                )
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}
```

- Always wrap previews in `AppTheme`.
- Use realistic sample data, not empty states (unless previewing the empty/loading/error state specifically — add separate previews for those).
- Preview `Screen` composables only; `Root` composables need a ViewModel and are not previewable.
- Previews are `private`.

---

## Accessibility

Use a meaningful `contentDescription` on all interactive or informational visual elements, always from string resources:

```kotlin
Icon(
    imageVector = Icons.Default.Delete,
    contentDescription = stringResource(R.string.cd_delete_note)
)
```

- Decorative elements that convey no information: `contentDescription = null`.
- Interactive elements must have a touch target of at least 48dp (Material components handle this; custom `clickable` elements need `Modifier.minimumInteractiveComponentSize()`).
- Custom clickable rows: pass `role` (e.g. `Role.Button`) to `clickable`, and use `Modifier.semantics(mergeDescendants = true) { }` so TalkBack reads the row as one element.
- An icon inside a button that already has a text label gets `contentDescription = null` to avoid double announcement.
- Test tags (only when there is nothing user-visible to match — see `testing` → Compose UI Tests) are constants in `object <Screen>TestTags` in the same file as the Screen composable:

```kotlin
object NoteListTestTags {
    const val EMPTY_STATE = "note_list_empty_state"
}

Box(modifier = Modifier.testTag(NoteListTestTags.EMPTY_STATE)) { /* ... */ }
```

---

## Text Input

Use the state-based `TextField(state = ...)` / `BasicTextField(state = ...)` with a `TextFieldState` held **in the ViewModel**. Do not drive a text field from `StateFlow` via `value` / `onValueChange` — the asynchronous round-trip through the flow can drop characters and jump the cursor.

`TextFieldState` is the one exception to "screen state lives in `State`": it is exposed as a separate property of the ViewModel.

```kotlin
// ViewModel
val titleState = TextFieldState()

// Screen composable receives it as a parameter
@Composable
fun NoteEditorScreen(
    state: NoteEditorState,
    titleState: TextFieldState,
    onAction: (NoteEditorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { innerPadding ->
        TextField(
            state = titleState,
            label = { Text(stringResource(R.string.title)) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
```

The ViewModel reacts to text changes with `snapshotFlow { titleState.text }` (validation, persistence to `SavedStateHandle`) — see the `presentation-mvi` skill for the full pattern.
