---
name: presentation-mvi
description: |
  MVI presentation layer for Android with Dagger Hilt and Navigation 3 - State, Action, Event, @HiltViewModel, assisted injection of navigation keys, Root/Screen composable split, UI models, UiText error mapping, injected CoroutineDispatchers, JUnit5 ViewModel tests, and process death with SavedStateHandle. Use this skill whenever creating or reviewing a ViewModel, defining screen state, actions, or events, structuring composables, wiring Hilt into the presentation layer, passing navigation arguments to a ViewModel, injecting dispatchers, testing ViewModels, mapping errors to UI strings, or handling process death. Trigger on phrases like "add a ViewModel", "create a screen", "MVI", "state", "action", "event", "screen composable", "Hilt", "hiltViewModel", "@HiltViewModel", "AssistedInject", "NavKey", "dispatcher", "withContext", "UiText", "SavedStateHandle", "ObserveAsEvents", "ViewModel test", or "UI model".
---

# Presentation Layer (MVI + Hilt + Navigation 3)

For where modules and Hilt modules live, see the `module-structure` skill. Navigation wiring follows the `navigation` skill (see [Registering entries](#registering-entries-navigation-3--hilt)).

## Overview

Every screen has:
1. **State** — a single data class holding all UI state fields.
2. **Action** (Intent) — a sealed interface of all user-triggered actions.
3. **Event** — a sealed interface of one-time side effects (navigation, snackbar).
4. **ViewModel** — a `@HiltViewModel` that holds `StateFlow<State>`, processes `Action`, emits `Event` via `Channel`.

`Dispatchers.IO` / `Dispatchers.Default` are **always injected**, never hardcoded (see [Coroutine Dispatchers](#coroutine-dispatchers)). The only hardcoded dispatcher is `Dispatchers.Main.immediate` inside `ObserveAsEvents`.

---

## State

```kotlin
data class NoteListState(
    val notes: List<NoteUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null
)
```

Always update state with `.update { }` — never replace the entire flow:
```kotlin
_state.update { it.copy(isLoading = true) }
```

---

## Action (Intent)

```kotlin
sealed interface NoteListAction {
    data object OnRefreshClick : NoteListAction
    data class OnNoteClick(val noteId: String) : NoteListAction
}
```

---

## Event (one-time side effects)

```kotlin
sealed interface NoteListEvent {
    data class NavigateToDetail(val noteId: String) : NoteListEvent
    data class ShowSnackbar(val message: UiText) : NoteListEvent
}
```

---

## ViewModel

Annotate with `@HiltViewModel` and use `@Inject constructor`. Depend on interfaces (repositories, use cases), never on concrete data-layer classes.

```kotlin
@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState())
    val state = _state.asStateFlow()

    private val _events = Channel<NoteListEvent>()
    val events = _events.receiveAsFlow()

    init {
        noteRepository.observeNotes()
            .onEach { notes ->
                _state.update { state -> state.copy(notes = notes.map { it.toNoteUi() }) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: NoteListAction) {
        when (action) {
            NoteListAction.OnRefreshClick -> refresh()
            is NoteListAction.OnNoteClick -> {
                viewModelScope.launch {
                    _events.send(NoteListEvent.NavigateToDetail(action.noteId))
                }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            noteRepository.refreshNotes()
                .onFailure { error ->
                    _events.send(NoteListEvent.ShowSnackbar(error.toUiText()))
                }
            _state.update { it.copy(isLoading = false) }
        }
    }
}
```

The list comes from `observeNotes()` (Room is the source of truth); `refresh()` only reports failures. See `data-layer` → Offline-First.

`onSuccess` / `onFailure` here are the project's own `AppResult<D, E : RootError>` from `core:domain`, not `kotlin.Result` — `error` is a typed `DataError`, not a `Throwable`.

ViewModels do **not** switch dispatchers themselves. `viewModelScope` runs on `Dispatchers.Main.immediate`, and every suspend function called from it must be **main-safe** (the repository / data source moves work off the main thread).

### ViewModel with navigation arguments

With Navigation 3, arguments are fields of the `NavKey`. They are **not** delivered through `SavedStateHandle`. Pass them into the ViewModel with Hilt assisted injection:

```kotlin
// feature:notes:presentation — only reachable from within notes
@Serializable
data class NoteDetailKey(val noteId: String) : NavKey

// feature:notes:presentation
@HiltViewModel(assistedFactory = NoteDetailViewModel.Factory::class)
class NoteDetailViewModel @AssistedInject constructor(
    @Assisted private val noteId: String,
    private val noteRepository: NoteRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(noteId: String): NoteDetailViewModel
    }

    // ...
}
```

Pass primitive fields (or the whole key) as `@Assisted` params. If two assisted params share a type, give them identifiers: `@Assisted("noteId")`.

---

## Coroutine Dispatchers

Follow the official Android recommendation: **never hardcode `Dispatchers.IO` / `Dispatchers.Default`**. Inject them via Hilt with qualifiers so tests can replace them with a `TestDispatcher`.

Qualifiers live in `core:domain` (pure `javax.inject`, so domain use cases can use them); `DispatchersModule` lives in `core:data`.

```kotlin
// core:domain
@Qualifier @Retention(AnnotationRetention.BINARY) annotation class IoDispatcher
@Qualifier @Retention(AnnotationRetention.BINARY) annotation class DefaultDispatcher
@Qualifier @Retention(AnnotationRetention.BINARY) annotation class MainDispatcher

// core:data
@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
```

Any class that switches context takes the dispatcher as a constructor parameter:

```kotlin
class ImageCompressor @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun compress(bytes: ByteArray): ByteArray = withContext(ioDispatcher) {
        // blocking compression logic
    }
}

class SortNotesUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(notes: List<Note>): List<Note> = withContext(defaultDispatcher) {
        notes.sortedWith(/* expensive comparator */)
    }
}
```

Rules:
- `@IoDispatcher` for blocking I/O (files, legacy synchronous APIs, blocking SDKs).
- `@DefaultDispatcher` for CPU-heavy work (large parsing, sorting big lists, image processing).
- Room and Retrofit suspend functions are already main-safe — don't wrap them in `withContext` without a reason.
- `@MainDispatcher` is rarely needed; inject it only for classes outside `viewModelScope` that must post to main (e.g., an application-scoped coordinator).

---

## Testing (JUnit5)

Local unit tests (`src/test`) run on JUnit5 (android-junit5 plugin). JUnit4 `@Rule` / `TestWatcher` do **not** work there — use extensions. Instrumented Compose UI tests still use JUnit4 `@get:Rule` (see the `testing` skill).

### Classes with injected dispatchers

Pass a `TestDispatcher` that shares the test's scheduler:

```kotlin
class SortNotesUseCaseTest {

    @Test
    fun `sorts notes`() = runTest {
        val sortNotes = SortNotesUseCase(
            defaultDispatcher = StandardTestDispatcher(testScheduler)
        )
        val result = sortNotes(listOf(testNote(id = "2"), testNote(id = "1")))
        // ...
    }
}
```

### ViewModels

Replace `Dispatchers.Main` with an extension (it lives in `:core:testing` — see the `testing` skill):

```kotlin
class MainDispatcherExtension(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext) = Dispatchers.setMain(testDispatcher)
    override fun afterEach(context: ExtensionContext) = Dispatchers.resetMain()
}

class NoteListViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @Test
    fun `notes from repository appear in state`() = runTest {
        val viewModel = NoteListViewModel(FakeNoteRepository())
        viewModel.state.test {
            // Turbine + AssertK assertions
        }
    }
}
```

The list comes from `observeNotes()`; `refresh()` only reports failures — full state, loading and event tests are in the `testing` skill → ViewModel Unit Tests.

`@JvmField` is required — JUnit5 needs a non-private field, and a plain Kotlin `val` compiles to a private field with a getter.

Instantiate ViewModels directly in unit tests (constructor + fakes). Assisted ViewModels are constructed the same way: `NoteDetailViewModel(noteId = "1", noteRepository = FakeNoteRepository())`.

---

## Mapping Errors to UI Strings

`UiText` (`core:presentation`) wraps strings that originate from — or could originate from — a string resource:

```kotlin
@Immutable
sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    data class StringResource(
        @StringRes val id: Int,
        val args: List<Any> = emptyList()
    ) : UiText
}
```

`@Immutable` on the interface makes `UiText` — and every state or event that holds it — stable for the Compose compiler, so `*State` classes need no annotation of their own. `args` is a `List`, not an `Array`, so `StringResource` gets a structural `equals` and can be compared directly in tests.

**When to use `UiText`:** For any string that comes from a string resource, could be localized, or might be either a resource or a dynamic value depending on context (e.g., error messages that map to `R.string.*`).

**When to use plain `String`:** For values that are always dynamic and never come from resources — e.g., a user's name, a formatted date, a currency amount. Expose these as `String` directly in the state or UI model.

```kotlin
// UiText — error message that maps to a string resource
data class NoteListState(
    val error: UiText? = null
)

// Plain String — always dynamic, never a resource
data class NoteUi(
    val id: String,
    val title: String,         // user content
    val formattedDate: String  // formatted from Note.createdAt
)
```

`DataError.toUiText()` lives only in `core:presentation`; feature-specific errors get their `toUiText()` in the feature's `presentation` module. Both map error enums to `UiText.StringResource`. A feature needing different wording for a `DataError` maps it to its own error type with `mapError` first (see `error-handling`) — never define a second `DataError.toUiText()`.

Never inject `Context` into a ViewModel to resolve strings — resolve `UiText` in the UI:

```kotlin
// core:presentation
@Composable
fun UiText.asString(): String = when (this) {
    is UiText.DynamicString -> value
    is UiText.StringResource -> stringResource(id, *args.toTypedArray())
}

fun UiText.asString(context: Context): String = when (this) {
    is UiText.DynamicString -> value
    is UiText.StringResource -> context.getString(id, *args.toTypedArray())
}
```

Use the `@Composable` version in composables; use `asString(context)` inside `ObserveAsEvents` callbacks (not composable), e.g. `is ShowSnackbar -> scope.launch { snackbarHostState.showSnackbar(event.message.asString(context)) }` with `context` (`LocalContext.current`) and `scope` (`rememberCoroutineScope()`) captured in the Root — `showSnackbar` is a suspend function and `onEvent` is not (full example in [Composable Structure](#composable-structure)).

---

## UI Model (Presentation Model)

Composables never receive domain models. Map every domain model that reaches the UI to a `*Ui` model in `presentation`, even when the fields are 1:1 — domain modules have no Compose compiler, so their classes are unstable (see the `compose` skill → Stability & Recomposition). Formatting (dates, units, currency) also happens in this mapping:

```kotlin
data class NoteUi(
    val id: String,
    val title: String,
    val formattedDate: String  // e.g. "Mar 15, 2026"
)

fun Note.toNoteUi(): NoteUi = NoteUi(
    id = id,
    title = title,
    formattedDate = createdAt.format(...)
)
```

UI models are always suffixed with `Ui` (e.g., `NoteUi`, `TodoItemUi`).

---

## Composable Structure

Both the Root and Screen composable live in the **same file** (e.g., `NoteListScreen.kt`).

### Root Composable (suffixed `Root`)

Receives the ViewModel (via `hiltViewModel()`) and any callbacks needed for navigation. Observes events. Passes `state`, `onAction`, and the holders Screen needs (`TextFieldState`s from the ViewModel, a `SnackbarHostState` it creates) down.

Both Root and Screen take `modifier: Modifier = Modifier` as the first optional parameter (see the `compose` skill → Composable API Conventions). Root passes it straight to Screen, which applies it to its root element. In Root, `viewModel` is always the **last** parameter.

Events are collected with `ObserveAsEvents` from `core:presentation`:

```kotlin
// core:presentation
@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current // androidx.lifecycle.compose
    val currentOnEvent by rememberUpdatedState(onEvent)
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2, flow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect { currentOnEvent(it) }
            }
        }
    }
}
```

- `repeatOnLifecycle(STARTED)` — events aren't handled while the screen is in the background; the `Channel` buffers them until it returns.
- `Dispatchers.Main.immediate` is a deliberate exception to "never hardcode dispatchers" (which targets `IO` / `Default` in testable classes): it prevents an event from being lost between being sent and collected.

### Screen Composable (suffixed `Screen`)

Receives `state`, `onAction`, and — as separate parameters — only these holders:
- the ViewModel's `TextFieldState`s, for text input (see [Process Death](#process-death) and the `compose` skill → Text Input);
- a `SnackbarHostState` created in the Root, if the screen shows snackbars.

No ViewModel reference. Can be previewed independently.

Each Screen owns its `Scaffold` (top bar, snackbar host): `modifier` goes on the `Scaffold`, and `innerPadding` goes on the content (e.g. `contentPadding` of a `LazyColumn`). `MainActivity` has no `Scaffold` of its own — see [Registering entries](#registering-entries-navigation-3--hilt).

```kotlin
// NoteListScreen.kt — Root + Screen in a single file

@Composable
fun NoteListRoot(
    onNavigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is NoteListEvent.NavigateToDetail -> onNavigateToDetail(event.noteId)
            is NoteListEvent.ShowSnackbar -> scope.launch {
                snackbarHostState.showSnackbar(event.message.asString(context))
            }
        }
    }

    NoteListScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun NoteListScreen(
    state: NoteListState,
    snackbarHostState: SnackbarHostState,
    onAction: (NoteListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) { /* ... */ }
    }
}

@PreviewLightDark
@Composable
private fun NoteListScreenPreview() {
    AppTheme {
        NoteListScreen(
            state = NoteListState(
                notes = listOf(NoteUi(id = "1", title = "Leg day", formattedDate = "Mar 15"))
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}
```

Preview rules (realistic data, separate previews for empty/loading/error): see the `compose` skill → Previews.

Root for a screen with navigation arguments:

```kotlin
@Composable
fun NoteDetailRoot(
    key: NoteDetailKey,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteDetailViewModel = hiltViewModel<NoteDetailViewModel, NoteDetailViewModel.Factory>(
        creationCallback = { factory -> factory.create(key.noteId) }
    )
) { /* same pattern */ }
```

### Registering entries (Navigation 3 + Hilt)

Navigation follows the modular Hilt recipe as adapted in the `navigation` skill → Conventions (no per-feature `api` modules; `Navigator` is not a Hilt binding). Shared navigation types live in `:core:navigation`:

```kotlin
// core:navigation
typealias EntryProviderInstaller = EntryProviderScope<NavKey>.(Navigator) -> Unit

class Navigator(val backStack: NavBackStack<NavKey>) {

    fun goTo(destination: NavKey) {
        backStack.add(destination)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }
}

// Keys of screens reachable from other features (or used as the start destination)
@Serializable
data object NoteListKey : NavKey

// feature:notes:presentation — keys of screens reachable only from within the feature
@Serializable
data class NoteDetailKey(val noteId: String) : NavKey
```

A key moves to `:core:navigation` only when another feature needs to navigate to it — same rule as any other code (see `module-structure` → Core Philosophy).

Each feature's `presentation` module contributes its entries to a Hilt multibinding set. The installer receives the `Navigator` as a parameter and is the only place that touches it; Root composables still receive navigation as callbacks:

```kotlin
// feature:notes:presentation
@Module
@InstallIn(ActivityRetainedComponent::class)
object NoteNavigationModule {

    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(): EntryProviderInstaller = { navigator ->
        entry<NoteListKey> {
            NoteListRoot(onNavigateToDetail = { id -> navigator.goTo(NoteDetailKey(id)) })
        }
        entry<NoteDetailKey> { key ->
            NoteDetailRoot(key = key, onBack = { navigator.goBack() })
        }
    }
}
```

`:app` creates the back stack with `rememberNavBackStack` — so it survives configuration changes **and process death** (keys are `@Serializable NavKey`; on Android `NavKeySerializer` handles keys from any module without registration) — wraps it in a `Navigator`, injects the set of installers, and builds `NavDisplay`. `Navigator` is not provided by Hilt: a Hilt-scoped object can't own a saveable back stack. For ViewModels to be scoped to their back stack entry (and cleared when it is popped), `NavDisplay` must include the ViewModel decorator:

```kotlin
// :app
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var entryProviderInstallers: Set<@JvmSuppressWildcards EntryProviderInstaller>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val backStack = rememberNavBackStack(NoteListKey)
                val navigator = remember(backStack) { Navigator(backStack) }

                NavDisplay(
                    backStack = backStack,
                    onBack = { navigator.goBack() },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        entryProviderInstallers.forEach { install -> install(navigator) }
                    }
                )
            }
        }
    }
}
```

`MainActivity` has no `Scaffold`: each Screen composable owns its `Scaffold` (top bar, snackbar host) and applies its `innerPadding` to its content. An app-level `Scaffold` is added only for shared chrome (bottom navigation) — then it wraps `NavDisplay`, and the padding it passes down goes through `consumeWindowInsets` so the screens' own `Scaffold`s don't apply the system insets twice.

Without `rememberViewModelStoreNavEntryDecorator()`, `hiltViewModel()` scopes to the Activity and ViewModels leak across screens.

Hilt setup requirements:
- The `Application` class is annotated with `@HiltAndroidApp`, the hosting `Activity` with `@AndroidEntryPoint`.
- `hiltViewModel()` comes from the `androidx.hilt:hilt-lifecycle-viewmodel-compose` artifact (not the older `hilt-navigation-compose`, which is tied to Navigation 2).

---

## Process Death

When a screen involves complex forms or critical user input, restore essential fields using `SavedStateHandle`. Hilt provides `SavedStateHandle` automatically — add it to the constructor (works alongside `@Assisted` params too). With Navigation 3 it holds only what you put into it — navigation arguments come from the key.

Text fields use a `TextFieldState` held in the ViewModel (see the `compose` skill). Restore its initial value from `SavedStateHandle` and write changes back via `snapshotFlow`:

```kotlin
@HiltViewModel
class NoteEditorViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository
) : ViewModel() {

    val titleState = TextFieldState(initialText = savedStateHandle[KEY_TITLE] ?: "")

    private val _state = MutableStateFlow(NoteEditorState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            snapshotFlow { titleState.text.toString() }
                .collectLatest { title ->
                    savedStateHandle[KEY_TITLE] = title
                    _state.update { it.copy(canSave = title.isNotBlank()) }
                }
        }
    }

    private companion object {
        const val KEY_TITLE = "title"
    }
}
```

The Root passes the holder straight through: `NoteEditorScreen(state = state, titleState = viewModel.titleState, onAction = viewModel::onAction)`. Previews and UI tests pass `TextFieldState("…")` (see the `compose` skill → Text Input).

Non-text fields (selected option, toggles) are saved from `onAction` as usual: write to `SavedStateHandle`, then `_state.update { }`.

Only save what truly matters after process death — not the entire state.

---

## Naming Conventions

| Thing | Convention | Example |
|---|---|---|
| ViewModel | `<Screen>ViewModel` | `NoteListViewModel` |
| State | `<Screen>State` | `NoteListState` |
| Action | `<Screen>Action` | `NoteListAction` |
| Event | `<Screen>Event` | `NoteListEvent` |
| Root composable | `<Screen>Root` | `NoteListRoot` |
| Screen composable | `<Screen>Screen` | `NoteListScreen` |
| Navigation key | `<Screen>Key` | `NoteListKey`, `NoteDetailKey` |
| Navigation Hilt module | `<Feature>NavigationModule` | `NoteNavigationModule` |
| UI model | `<Model>Ui` | `NoteUi`, `TodoItemUi` |
| Dispatcher qualifier | `<Name>Dispatcher` | `@IoDispatcher`, `@DefaultDispatcher` |

---

## Checklist: Adding a New Screen

- [ ] Define `State`, `Action`, `Event` in `feature:<name>:presentation`
- [ ] Define the `@Serializable` `NavKey`: in `:core:navigation` if another feature navigates to it, otherwise in `feature:<name>:presentation`
- [ ] Implement the ViewModel: `@HiltViewModel` + `@Inject constructor`, or `@AssistedInject` + `@AssistedFactory` if it needs key arguments
- [ ] Inject `@IoDispatcher` / `@DefaultDispatcher` via qualifiers in any class that calls `withContext` — never hardcode `Dispatchers.IO` / `Dispatchers.Default`
- [ ] Create `<Screen>Root` (gets ViewModel via `hiltViewModel()`, observes events, passes `modifier` to Screen; `viewModel` last) and `<Screen>Screen` (`state` + `onAction` + `TextFieldState` / `SnackbarHostState` params when needed, owns its `Scaffold`, previewable)
- [ ] Add the screen's `entry<Key>` to the feature's `@IntoSet` `EntryProviderInstaller` in `<Feature>NavigationModule`; navigate via `Navigator` inside the installer, not in composables
- [ ] Map domain models to `*Ui` models; State and composables never hold domain types
- [ ] Map any domain errors to `UiText` via extension functions
- [ ] Use `TextFieldState` in the ViewModel for text input; persist fields that must survive process death via `SavedStateHandle`
- [ ] Tests: `StandardTestDispatcher(testScheduler)` for classes with injected dispatchers; `MainDispatcherExtension` for ViewModels
