---
name: di-hilt
description: |
  Dagger Hilt dependency injection for Android - @Inject constructors, @Binds vs @Provides modules per layer, components and scopes, qualifiers, @HiltViewModel and hiltViewModel(), assisted injection, @ApplicationContext, DataStore/Room/Retrofit providers, WorkManager with @HiltWorker, and testing with Hilt. Use this skill whenever setting up Hilt, defining a DI module, providing a repository, data source, or ViewModel, injecting a dependency, choosing a scope, adding a qualifier, wiring the Application class, or replacing bindings in tests. Trigger on phrases like "set up Hilt", "Dagger", "add a Hilt module", "inject a dependency", "DI module", "@Inject", "@Binds", "@Provides", "@Singleton", "scope", "qualifier", "@Named", "hiltViewModel", "provide a ViewModel", "@HiltAndroidApp", "@AndroidEntryPoint", "@HiltWorker", or "@TestInstallIn".
---

# Dependency Injection (Dagger Hilt)

## Related Skills

- **module-structure** — which module each Hilt module lives in, and why `:app` must depend on every `data` module.
- **presentation-mvi** — `@HiltViewModel`, assisted injection of Navigation 3 keys, dispatcher qualifiers.
- **data-layer** — `NetworkModule`, Retrofit services, auth interceptor/authenticator bindings.

---

## Principles

- **`@Inject constructor` first.** A class you own gets `@Inject constructor` and needs no module at all. Write a module only to bind an interface or to construct something you don't own.
- **`@Binds` for interfaces, `@Provides` for everything else.** `@Binds` maps an implementation to its interface; `@Provides` builds third-party objects (Retrofit, Room, DataStore) or calls factory methods.
- **Modules live next to what they provide** — a feature's `data` module binds that feature's repositories. There is no central list of modules: Hilt discovers every `@InstallIn` module on the `:app` classpath.
- **Unscoped by default.** Add a scope only when the instance must be shared (holds state, a cache, a connection) or is expensive to create.
- **Inject ViewModels at the Root composable only** via `hiltViewModel()`. Never pass ViewModels down the composable tree.

---

## Application and Entry Points (`:app`)

```kotlin
@HiltAndroidApp
class App : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() { /* setContent { ... } */ }
```

Unlike Koin, there is no `startKoin { modules(...) }` equivalent — no manual assembly. Hilt collects `@InstallIn` modules from `:app`'s runtime classpath, including transitive dependencies (Hilt Gradle plugin, `enableAggregatingTask`, on by default — never disable it). `:app` must depend directly on:
- every feature `data` and `presentation` module — nothing else depends on them;
- `core:data` — `DispatchersModule` serves domain and presentation code, which doesn't depend on `core:data`;
- any module whose types `:app` uses directly (e.g. `core:navigation` for `Navigator` in `MainActivity`).

`core:database` arrives transitively through the feature `data` modules that use it (see `module-structure`). A missing `@Binds` / `@Provides` fails the build with `MissingBinding`, but a missing `@IntoSet` contribution (e.g. a feature's `EntryProviderInstaller`) is silently absent from the set and only fails at runtime.

---

## Module Definitions

Keep `@Binds` and `@Provides` in separate declarations: `@Binds` requires an `abstract class` or `interface`, `@Provides` works best in an `object`.

### Feature data layer — bind interfaces

```kotlin
// feature:notes:data
@Module
@InstallIn(SingletonComponent::class)
abstract class NoteDataModule {

    // Unscoped: no state of its own (Room is the source of truth).
    // Scope it only when it holds a cache or a Mutex.
    @Binds
    abstract fun bindNoteRepository(impl: OfflineFirstNoteRepository): NoteRepository

    @Binds
    abstract fun bindNoteLocalDataSource(impl: RoomNoteDataSource): NoteLocalDataSource

    @Binds
    abstract fun bindNoteRemoteDataSource(impl: RetrofitNoteDataSource): NoteRemoteDataSource
}

// feature:notes:data — third-party object (Retrofit service)
@Module
@InstallIn(SingletonComponent::class)
object NoteNetworkModule {

    @Provides
    @Singleton
    fun provideNoteApi(retrofit: Retrofit): NoteApi = retrofit.create(NoteApi::class.java)
}
```

The implementations just declare their dependencies:

```kotlin
class OfflineFirstNoteRepository @Inject constructor(
    private val localDataSource: NoteLocalDataSource,
    private val remoteDataSource: NoteRemoteDataSource
) : NoteRepository { /* ... */ }
```

Put the scope annotation on the `@Binds` / `@Provides` method, not on the implementation class, so all scoping decisions for a feature are visible in one place.

### Presentation layer — no module needed

```kotlin
// feature:notes:presentation
@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel()
```

`@HiltViewModel` + `@Inject constructor` is all Hilt needs. For ViewModels that receive a navigation key, use `@AssistedInject` (see `presentation-mvi`).

### Core modules

```kotlin
// core:data
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("settings")
    }
}

// core:database
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao = database.noteDao()
}
```

Other core modules: `NetworkModule` (Json, OkHttpClient, Retrofit — see `data-layer`) and `DispatchersModule` (see `presentation-mvi`), both in `core:data`.

DataStore and Room **must** be `@Singleton` — two DataStore instances for the same file throw at runtime, and two Room instances break invalidation tracking. DAOs stay unscoped: Room already caches them.

---

## Context

Never inject a raw `Context` — always qualify it:
- `@ApplicationContext context: Context` — the default; safe in singletons.
- `@ActivityContext` — only in `ActivityComponent`-scoped bindings; never in ViewModels or singletons (leaks the Activity).

ViewModels never receive `Context` at all. Strings are resolved in the UI via `UiText` (see `presentation-mvi`).

---

## Components and Scopes

| Component | Scope annotation | Lifetime | Use for |
|---|---|---|---|
| `SingletonComponent` | `@Singleton` | Application | Retrofit, OkHttp, Room, DataStore, repositories holding a cache or shared state |
| `ActivityRetainedComponent` | `@ActivityRetainedScoped` | Survives configuration changes | State shared across ViewModels of the same Activity (rare) |
| `ViewModelComponent` | `@ViewModelScoped` | One ViewModel | A helper shared by several dependencies of one ViewModel |
| `ActivityComponent` | `@ActivityScoped` | One Activity instance | Objects needing `@ActivityContext` (rare in Compose apps) |
| any | *(none)* | New instance per injection | Default — stateless data sources, use cases, mappers |

Rules:
- The scope annotation must match the component in `@InstallIn`: `@Singleton` only in `SingletonComponent`, `@ViewModelScoped` only in `ViewModelComponent`, etc. A mismatch is a compile error.
- A binding may only depend on bindings from its own component or a parent component (`SingletonComponent` is the root). A `@Singleton` class can never depend on something `@ViewModelScoped`.
- Unscoped is the default. A scope costs a double-checked lock on every access — use it for shared state, not "just in case".

---

## Qualifiers

When two bindings share a type, distinguish them with a custom qualifier. Prefer custom annotations over `@Named("...")` — they are typo-safe and navigable in the IDE.

```kotlin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthClient

@Provides
@Singleton
@AuthClient
fun provideAuthRetrofit(json: Json): Retrofit = /* Retrofit without AuthInterceptor/TokenAuthenticator */

@Provides
@Singleton
fun provideRefreshTokenApi(@AuthClient retrofit: Retrofit): RefreshTokenApi =
    retrofit.create(RefreshTokenApi::class.java)
```

Dispatcher qualifiers (`@IoDispatcher`, `@DefaultDispatcher`, `@MainDispatcher`) live in `core:domain` — see `presentation-mvi`.

---

## Injecting in Composables

Always use `hiltViewModel()` in Root composables:

```kotlin
@Composable
fun NoteListRoot(
    onNavigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = hiltViewModel()
) { /* ... */ }
```

- `hiltViewModel()` comes from `androidx.hilt:hilt-lifecycle-viewmodel-compose`.
- With Navigation 3, `NavDisplay` must include `rememberViewModelStoreNavEntryDecorator()` so the ViewModel is scoped to its back stack entry (see `presentation-mvi`).
- Never inject non-ViewModel dependencies into composables (no `EntryPoint` lookups from Compose). If a composable needs something, it comes through the ViewModel's state or a callback.

---

## WorkManager

```kotlin
@HiltWorker
class SyncNotesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val noteRepository: NoteRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result =
        when (noteRepository.refreshNotes()) {
            is AppResult.Success -> Result.success()
            is AppResult.Error -> Result.retry()
        }
}
```

`Result` inside a worker is WorkManager's `ListenableWorker.Result`, not `AppResult`.

Wiring in `:app`:

```kotlin
@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()
}
```

Also remove the default `WorkManagerInitializer` from the manifest (`androidx.startup` `<provider>` with `tools:node="remove"` on the WorkManager initializer), otherwise WorkManager initializes before Hilt and the factory is ignored.

---

## Testing

- **Unit tests don't use Hilt.** Construct the class directly with fakes: `NoteListViewModel(FakeNoteRepository())`. This is why every dependency is a constructor parameter.
- **Instrumented tests** use `@HiltAndroidTest` with `HiltAndroidRule`, and a custom runner that returns `HiltTestApplication`. Only `:app`'s E2E tests use Hilt, so the runner lives in `:app/src/androidTest` and only `:app` points `testInstrumentationRunner` at it (set by the `android-application` convention plugin). Other modules keep the default `AndroidJUnitRunner`:

```kotlin
// :app/src/androidTest
class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application =
        super.newApplication(cl, HiltTestApplication::class.java.name, context)
}
```

- Replace production bindings for all instrumented tests with `@TestInstallIn`. The fake module lives in `:app/src/androidTest`; the fake itself comes from the domain module's test fixtures (`androidTestImplementation(testFixtures(project(":feature:notes:domain")))` — see `testing` → Where Test Code Lives). The fake needs an `@Inject constructor()` for `@Binds` to work:

```kotlin
// :app/src/androidTest
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NoteDataModule::class]
)
abstract class FakeNoteDataModule {
    // Scoped: the fake holds state shared by the test and the app.
    // Inject NoteRepository in the test and cast it to FakeNoteRepository —
    // injecting FakeNoteRepository directly would give a new, unscoped instance.
    @Binds
    @Singleton
    abstract fun bindNoteRepository(impl: FakeNoteRepository): NoteRepository
}
```

`replaces` swaps a whole module, which is another reason to keep modules small and per-feature.

---

## Gradle

The `dagger-hilt` convention plugin (see `module-structure`) applies the Hilt Gradle plugin, the Hilt runtime, and the Dagger compiler via **KSP** (not kapt). Pure Kotlin `domain` modules don't apply it — they only use `javax.inject` annotations.

---

## Naming Conventions

| Thing | Convention | Example |
|---|---|---|
| Module binding interfaces | `<Feature>DataModule` | `NoteDataModule` |
| Module providing third-party objects | `<Feature/Purpose>Module` | `NoteNetworkModule`, `NetworkModule`, `DatabaseModule`, `DataStoreModule` |
| `@Binds` method | `bind<Interface>` | `bindNoteRepository` |
| `@Provides` method | `provide<Type>` | `provideNoteApi` |
| Qualifier | describes the variant | `@AuthClient`, `@IoDispatcher` |
| Test replacement module | `Fake<Module>` | `FakeNoteDataModule` |

---

## Checklist: Adding DI for a New Feature

- [ ] Give every class you own an `@Inject constructor` (no module for them)
- [ ] Add a `@Binds` module (`<Feature>DataModule`) in `feature:<name>:data` for every interface → implementation
- [ ] Add a `@Provides` module in `feature:<name>:data` for Retrofit services or other third-party objects
- [ ] Decide scopes deliberately: unscoped by default, `@Singleton` only for shared state or expensive objects
- [ ] Annotate ViewModels with `@HiltViewModel` (use `@AssistedInject` if they take a navigation key)
- [ ] Use `hiltViewModel()` in all Root composables
- [ ] Make sure `:app` depends on the feature's `data` and `presentation` modules
- [ ] For instrumented tests, add a `@TestInstallIn` fake module if the feature talks to the network or DB
