---
name: testing
description: |
  Testing patterns for Android - ViewModel unit tests with JUnit5, Turbine, AssertK, MainDispatcherExtension, fakes for repositories and data sources, testing injected dispatchers, SavedStateHandle and TextFieldState, repository/Room/Retrofit/migration tests, Compose UI tests, Hilt instrumented tests, and the robot pattern. Use this skill whenever writing or reviewing tests for ViewModels, repositories, data sources, use cases, DAOs, migrations, or Compose screens. Trigger on phrases like "write a test", "unit test the ViewModel", "test a repository", "Turbine", "fake repository", "MainDispatcherExtension", "UnconfinedTestDispatcher", "StandardTestDispatcher", "runTest", "MockWebServer", "in-memory database", "ComposeTestRule", "robot", "HiltAndroidTest", or "JUnit5".
---

# Testing

## Stack

| Concern | Library |
|---|---|
| Local unit tests | JUnit5 (via `de.mannodermaus.android-junit5`) |
| Assertions | AssertK |
| Flow / StateFlow testing | Turbine |
| Coroutine testing | `kotlinx-coroutines-test` (`runTest`, `StandardTestDispatcher`, `UnconfinedTestDispatcher`) |
| Network tests | OkHttp `MockWebServer` |
| DB tests | Room in-memory database, `MigrationTestHelper` — instrumented only (`src/androidTest`) |
| UI tests | `ComposeTestRule` (instrumented, JUnit4 runner) |
| DI in instrumented tests | Hilt testing (`@HiltAndroidTest`, `@TestInstallIn`) |

JUnit5 is for **local** unit tests (`src/test`). Instrumented tests (`src/androidTest`) run on the JUnit4-based `AndroidJUnitRunner`, so Compose UI tests use `@get:Rule` and JUnit4's `@Test`. Don't mix JUnit4 and JUnit5 annotations in the same source set.

---

## Where Test Code Lives

| What | Where |
|---|---|
| Tests for a class | The same module's `src/test` or `src/androidTest` |
| Fakes of domain interfaces (feature `domain` or `core:domain`) + test data builders | `src/testFixtures` of the domain module that declares the interface |
| Fakes of data-layer interfaces (feature `data` data sources such as `NoteLocalDataSource`, `core:data` interfaces such as `TokenStorage`) | That module's `src/test` |
| `@TestInstallIn` fake Hilt modules, `HiltTestRunner` | `:app/src/androidTest` (only `:app` runs Hilt tests — see `di-hilt` → Testing) |
| `MainDispatcherExtension` and other generic test utilities — no fakes | `:core:testing` (added as `testImplementation`) |

A fake implements a domain interface, so it lives next to it — in the domain module's test fixtures (`java-test-fixtures`, applied by the `domain-module` convention plugin). Fixtures never reach production code, and the same fake serves unit tests and instrumented tests:

```kotlin
// feature:notes:presentation, feature:notes:data
testImplementation(testFixtures(project(":feature:notes:domain")))

// :app — E2E tests that swap bindings with @TestInstallIn
androidTestImplementation(testFixtures(project(":feature:notes:domain")))
```

Don't put fakes of domain interfaces in `src/test` (invisible to `androidTest` and other modules) or in `:core:testing` (it holds no fakes). Only fakes of data-layer interfaces, used solely by that module's own tests, go in `src/test`.

---

## ViewModel Unit Tests

### Main dispatcher

`viewModelScope` uses `Dispatchers.Main`, which doesn't exist on the JVM. Replace it with `MainDispatcherExtension` from `:core:testing`:

```kotlin
class MainDispatcherExtension(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext) = Dispatchers.setMain(testDispatcher)
    override fun afterEach(context: ExtensionContext) = Dispatchers.resetMain()
}
```

```kotlin
class NoteListViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    private lateinit var repository: FakeNoteRepository
    private lateinit var viewModel: NoteListViewModel

    @BeforeEach
    fun setUp() {
        repository = FakeNoteRepository()
        viewModel = NoteListViewModel(repository)
    }
}
```

`@JvmField` is required — JUnit5 needs a non-private field, and a plain Kotlin `val` compiles to a private field with a getter. Create the ViewModel in `@BeforeEach` (after the extension has set Main), not in a property initializer.

### Testing state with Turbine

A `StateFlow` always emits its **current value first**. Account for it with `awaitItem()` or `skipItems(1)` — otherwise the test asserts against the initial state.

```kotlin
@Test
fun `observed notes appear in state`() = runTest {
    repository.setNotes(listOf(testNote(id = "1", title = "Hello")))

    viewModel.state.test {
        assertThat(awaitItem().notes.map { it.title }).containsExactly("Hello")
    }
}
```

### Testing intermediate states

`StateFlow` is conflated: if the fake returns instantly, the `isLoading = true` state may never be observed. Make the fake suspend on a gate so the test controls when the operation completes:

```kotlin
@Test
fun `refresh toggles loading`() = runTest {
    val gate = CompletableDeferred<Unit>()
    repository.refreshGate = gate

    viewModel.state.test {
        skipItems(1) // initial state
        viewModel.onAction(NoteListAction.OnRefreshClick)
        assertThat(awaitItem().isLoading).isTrue()

        gate.complete(Unit)
        assertThat(awaitItem().isLoading).isFalse()
    }
}
```

### Testing events

Subscribe **before** triggering the action — events go through a `Channel`, and Turbine must be collecting when the event is sent.

```kotlin
@Test
fun `clicking note sends NavigateToDetail`() = runTest {
    viewModel.events.test {
        viewModel.onAction(NoteListAction.OnNoteClick("123"))
        assertThat(awaitItem()).isEqualTo(NoteListEvent.NavigateToDetail("123"))
    }
}

@Test
fun `failed refresh shows snackbar`() = runTest {
    repository.refreshError = DataError.Network.NO_INTERNET

    viewModel.events.test {
        viewModel.onAction(NoteListAction.OnRefreshClick)
        assertThat(awaitItem()).isEqualTo(
            NoteListEvent.ShowSnackbar(UiText.StringResource(R.string.error_no_internet))
        )
    }
}
```

`UiText.StringResource` is a `data class` (its `args` is a `List`), so events and states holding it compare structurally with `isEqualTo`.

### State and events in one test

Use `turbineScope` when an action affects both:

```kotlin
@Test
fun `failed refresh stops loading and shows snackbar`() = runTest {
    repository.refreshError = DataError.Network.NO_INTERNET

    turbineScope {
        val states = viewModel.state.testIn(backgroundScope)
        val events = viewModel.events.testIn(backgroundScope)
        states.skipItems(1)

        viewModel.onAction(NoteListAction.OnRefreshClick)

        assertThat(events.awaitItem()).isInstanceOf<NoteListEvent.ShowSnackbar>()
        assertThat(states.expectMostRecentItem().isLoading).isFalse()
    }
}
```

### ViewModels with assisted parameters

Construct them directly — no Hilt in unit tests:

```kotlin
val viewModel = NoteDetailViewModel(noteId = "1", noteRepository = repository)
```

---

## Fakes

Prefer **fakes** over mocks. A fake is a small in-memory implementation of the interface from `domain`, with knobs for failures and timing:

```kotlin
// feature:notes:domain/src/testFixtures
class FakeNoteRepository @Inject constructor() : NoteRepository {

    private val notes = MutableStateFlow<List<Note>>(emptyList())

    var refreshError: DataError? = null
    var refreshGate: CompletableDeferred<Unit>? = null

    fun setNotes(value: List<Note>) {
        notes.value = value
    }

    override fun observeNotes(): Flow<List<Note>> = notes

    override suspend fun refreshNotes(): EmptyResult<DataError> {
        refreshGate?.await()
        return refreshError?.let { AppResult.Error(it) } ?: AppResult.Success(Unit)
    }
}
```

- Fakes implement **exactly** the domain interface — if the interface changes, the fake stops compiling, which is the point.
- Expose failures as typed `DataError` values, not booleans, so tests can check each error mapping.
- Keep test data builders with defaults next to the fakes (in the domain module's `testFixtures`) so tests only specify what matters:

```kotlin
// feature:notes:domain/src/testFixtures
fun testNote(
    id: String = "1",
    title: String = "Note $id",
    createdAt: Instant = Instant.EPOCH
): Note = Note(id = id, title = title, createdAt = createdAt)
```

Mocks (MockK) are acceptable only for types you don't own and can't fake reasonably.

---

## Classes with Injected Dispatchers

Every class that calls `withContext` receives its dispatcher through the constructor (see `presentation-mvi` → Coroutine Dispatchers). In tests, pass a `StandardTestDispatcher` bound to the test's scheduler, so virtual time and `delay()` inside the class are controlled by `runTest`:

```kotlin
@Test
fun `compress shrinks image`() = runTest {
    val compressor = ImageCompressor(ioDispatcher = StandardTestDispatcher(testScheduler))

    val result = compressor.compress(largeImage)

    assertThat(result.size).isLessThan(largeImage.size)
}
```

Always share `testScheduler`. A dispatcher created with its own scheduler runs on a separate virtual clock, and `runTest` won't wait for it.

---

## SavedStateHandle and TextFieldState

Instantiate `SavedStateHandle` directly with the values to restore:

```kotlin
@Test
fun `restores title after process death`() = runTest {
    val handle = SavedStateHandle(mapOf("title" to "Draft"))
    val viewModel = NoteEditorViewModel(handle, repository)

    assertThat(viewModel.titleState.text.toString()).isEqualTo("Draft")
}
```

Editing a `TextFieldState` in a unit test doesn't notify `snapshotFlow` by itself — there is no Compose runtime sending snapshot notifications. Call `Snapshot.sendApplyNotifications()` after each edit:

```kotlin
@Test
fun `title edits are saved to SavedStateHandle`() = runTest {
    val handle = SavedStateHandle()
    val viewModel = NoteEditorViewModel(handle, repository)

    viewModel.titleState.setTextAndPlaceCursorAtEnd("New title")
    Snapshot.sendApplyNotifications()

    assertThat(handle.get<String>("title")).isEqualTo("New title")
}
```

---

## Data Layer Tests

| Subject | Approach |
|---|---|
| Repository (e.g. `OfflineFirstNoteRepository`) | Local unit test with fake local and remote data sources |
| Retrofit data source + `safeCall` mapping | Local unit test with `MockWebServer` and a real Retrofit instance |
| DAO | Room in-memory database, instrumented (`src/androidTest` of the module that owns the DAO — usually `core:database`) |
| Migrations | `MigrationTestHelper` against the committed schema JSON |

```kotlin
class RetrofitNoteDataSourceTest {

    private val server = MockWebServer()
    private lateinit var dataSource: RetrofitNoteDataSource

    @BeforeEach
    fun setUp() {
        server.start()
        val api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(NetworkModule.provideJson().asConverterFactory("application/json".toMediaType()))
            .build()
            .create(NoteApi::class.java)
        dataSource = RetrofitNoteDataSource(api)
    }

    @AfterEach
    fun tearDown() = server.shutdown()

    @Test
    fun `401 maps to UNAUTHORIZED`() = runTest {
        server.enqueue(MockResponse().setResponseCode(401))

        val result = dataSource.fetchNotes()

        assertThat(result).isEqualTo(AppResult.Error(DataError.Network.UNAUTHORIZED))
    }
}
```

Always use the production `Json` (`NetworkModule.provideJson()`) — a default `Json` rejects unknown keys and would test a different configuration than the app runs.

Cover every HTTP code and exception branch of `safeCall` once, in `core:data`. Feature data source tests only need the happy path plus one error.

---

## Compose UI Tests

Test `Screen` composables (`state` + `onAction` + the holders Screen takes — `TextFieldState`, `SnackbarHostState`; see `presentation-mvi` → Screen Composable), not `Root` — no ViewModel, no Hilt needed:

```kotlin
class NoteListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysNotes() {
        composeTestRule.setContent {
            AppTheme {
                NoteListScreen(
                    state = NoteListState(notes = listOf(NoteUi(id = "1", title = "Hello", formattedDate = "Mar 15"))),
                    snackbarHostState = remember { SnackbarHostState() },
                    onAction = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Hello").assertIsDisplayed()
    }
}
```

- Always wrap content in `AppTheme`.
- Find nodes by text or `contentDescription` first (that's what users and TalkBack see); use `testTag` only when there is nothing user-visible to match. Keep tags as constants next to the composable.
- Assert that actions are dispatched by capturing them in `onAction` — a click test without an assertion on the captured action tests nothing.

### End-to-end tests with Hilt

For flows that go through `Root`, ViewModel, and real navigation, use `@HiltAndroidTest` with `createAndroidComposeRule<MainActivity>()` and replace each feature's `<Feature>DataModule` with a `@TestInstallIn` module that binds the fake repository from the domain module's test fixtures (see `di-hilt` → Testing) — the app then never reaches the network or the DB. Keep these few — they are slow and cover only critical user flows.

---

## Robot Pattern (Complex UI / E2E Tests)

For screens with 3+ UI tests, shared setup/assertion sequences, or multi-step flows, use a **robot** that owns all `composeTestRule` interactions for the screen. Every robot function returns `this` for chaining:

```kotlin
class NoteListRobot(private val composeTestRule: ComposeContentTestRule) {

    fun setContent(
        state: NoteListState,
        onAction: (NoteListAction) -> Unit = {}
    ) = apply {
        composeTestRule.setContent {
            AppTheme {
                NoteListScreen(
                    state = state,
                    snackbarHostState = remember { SnackbarHostState() },
                    onAction = onAction
                )
            }
        }
    }

    fun assertNoteVisible(title: String) = apply {
        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }

    fun clickNote(title: String) = apply {
        composeTestRule.onNodeWithText(title).performClick()
    }

    fun assertEmptyState() = apply {
        composeTestRule.onNodeWithTag(NoteListTestTags.EMPTY_STATE).assertIsDisplayed()
    }
}
```

```kotlin
class NoteListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val robot by lazy { NoteListRobot(composeTestRule) }

    @Test
    fun showsEmptyState_whenNoNotes() {
        robot
            .setContent(NoteListState(notes = emptyList()))
            .assertEmptyState()
    }

    @Test
    fun clickingNote_dispatchesOnNoteClick() {
        val actions = mutableListOf<NoteListAction>()

        robot
            .setContent(
                state = NoteListState(notes = listOf(NoteUi(id = "1", title = "Hello", formattedDate = "Mar 15"))),
                onAction = { actions += it }
            )
            .clickNote("Hello")

        assertThat(actions).containsExactly(NoteListAction.OnNoteClick("1"))
    }
}
```

Robots contain interactions and UI assertions only — no business logic and no assertions on captured actions (those stay in the test, where the intent is visible).

---

## What to Test

- Unit-test every ViewModel: initial state, each Action, each Event, success and failure paths.
- Unit-test repositories and non-trivial domain logic (validation, use cases).
- Test `safeCall` / `safeDbCall` mappings once in `core:data`; test every `toUiText()` mapping once, in the module that defines it (`core:presentation` for shared errors, the feature's `presentation` for feature errors).
- Test every Room migration.
- Compose UI tests for screens with non-trivial rendering logic (empty/loading/error states); E2E tests only for critical flows.
- Fakes over mocks. Every test asserts something — a test that only performs actions is not a test.

---

## Naming

| Thing | Convention | Example |
|---|---|---|
| Test class | `<Subject>Test` | `NoteListViewModelTest` |
| Local test name | backticked sentence | `` `failed refresh shows snackbar` `` |
| Instrumented test name | `subject_condition_expectation` (backticks with spaces aren't allowed in Android test method names on older runtimes) | `clickingNote_dispatchesOnNoteClick` |
| Fake | `Fake<Interface>` | `FakeNoteRepository` |
| Test data builder | `test<Model>` | `testNote(id = "1")` |
| Robot | `<Screen>Robot` | `NoteListRobot` |
