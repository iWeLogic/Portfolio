---
name: data-layer
description: |
  Data layer patterns for Android - data sources vs repositories, DTOs, mappers, Room entities and DAOs, Retrofit services, OkHttp/Retrofit setup with Hilt, auth interceptor and token refresh, token storage, Room migrations, and offline-first. Use this skill whenever writing or reviewing a data source or repository, creating DTOs or Room entities, writing mappers, defining a Retrofit service, setting up OkHttp or Retrofit, adding interceptors, handling network errors, implementing token refresh, or adding a DAO or migration. Trigger on phrases like "create a repository", "create a data source", "add a DAO", "Retrofit", "OkHttp", "interceptor", "Authenticator", "API service", "write a mapper", "DTO", "Room entity", "migration", "network call", "token storage", "token refresh", or "offline-first".
---

# Data Layer

## Related Skills

- **error-handling** — `AppResult<D, E>`, `EmptyResult`, `RootError`, `DataError`, the `map`/`mapError`/`flatMap`/`onSuccess`/`onFailure` helpers, `safeCall` (Retrofit), `safeDbCall` (Room), and the Flow error strategy. This skill uses them but does not redefine them.
- **module-structure** — where `core:data`, `core:database`, and feature `data` modules live and what they may depend on.

---

## Data Source vs Repository

- **Data source** — accesses a single source (Room, a Retrofit API, the file system, DataStore). Internal to the data layer.
- **Repository** — the **only** entry point to the data layer. ViewModels and use cases always depend on a repository, never on a data source — even when the repository wraps a single data source (e.g. a Room-only feature). A repository coordinates one or more data sources (e.g. Retrofit + Room for offline-first).

```kotlin
// Data sources — one per source, internal to the data layer (feature:notes:data)
interface NoteLocalDataSource {
    fun observeNotes(): Flow<List<Note>>
    suspend fun getNote(id: String): AppResult<Note, DataError.Local>
    suspend fun upsertNotes(notes: List<Note>): EmptyResult<DataError.Local>
}

interface NoteRemoteDataSource {
    suspend fun fetchNotes(): AppResult<List<Note>, DataError.Network>
}

// Repository — the only entry point for ViewModels and use cases (feature:notes:domain); here it combines both data sources
interface NoteRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun refreshNotes(): EmptyResult<DataError>
}
```

### Where interfaces live

- **Repository interfaces live in the feature's `domain` module.** This enforces that `presentation` never depends on `data` and makes testing with fakes trivial.
- **Data source interfaces always live in the feature's `data` module** (e.g. `NoteLocalDataSource` and `NoteRemoteDataSource` behind `OfflineFirstNoteRepository`) — nothing outside the data layer sees them.

Domain modules are pure Kotlin — no Android or framework imports (see `module-structure` for the allowed exceptions).

---

## DTOs, Entities, and Domain Models

- Always separate: **DTOs** (network, `data`) ↔ **Entities** (Room, `data` or `core:database`) ↔ **Domain models** (`domain`).
- Domain models never go directly into Room entities or Retrofit request/response bodies.
- DTOs are `@Serializable` with `@SerialName` for every field whose JSON name differs from the Kotlin name — never rely on the backend matching Kotlin naming.
- Mappers are plain extension functions in the feature's `data` module — the only module that sees both the DTO/entity and the domain model. DTO mappers sit next to the DTO. Mappers for entities from `core:database` also live in the feature's `data` module, never in `core:database` (it has no access to feature domain models). If two features use the same entity, each maps it to its own domain model.

```kotlin
// feature:notes:domain — the domain model used in all skill examples
data class Note(
    val id: String,
    val title: String,
    val createdAt: Instant
)

// feature:notes:data
@Serializable
data class NoteDto(
    val id: String,
    val title: String,
    @SerialName("created_at") val createdAt: Long
)

fun NoteDto.toNote(): Note = Note(id = id, title = title, createdAt = Instant.ofEpochMilli(createdAt))
fun Note.toNoteDto(): NoteDto = NoteDto(id = id, title = title, createdAt = createdAt.toEpochMilli())
// NoteEntity lives in core:database (shared DB) — its mappers still stay in feature:notes:data
fun NoteEntity.toNote(): Note = /* ... */
fun Note.toNoteEntity(): NoteEntity = /* ... */
```

---

## Implementations

Name implementations for what makes them unique — never suffix with `Impl`. All implementations use `@Inject constructor` and are bound to their interface with `@Binds` in a Hilt module in the same `data` module.

Room and Retrofit `suspend` functions are already main-safe — do not wrap them in `withContext`. Inject a dispatcher (`@IoDispatcher` / `@DefaultDispatcher`) only for genuinely blocking or CPU-heavy work (see `presentation-mvi` → Coroutine Dispatchers).

### Local data source (Room)

```kotlin
class RoomNoteDataSource @Inject constructor(
    private val noteDao: NoteDao
) : NoteLocalDataSource {

    override fun observeNotes(): Flow<List<Note>> =
        noteDao.observeAll().map { entities -> entities.map { it.toNote() } }

    override suspend fun getNote(id: String): AppResult<Note, DataError.Local> =
        safeDbCall { noteDao.getById(id) }.flatMap { entity ->
            entity?.let { AppResult.Success(it.toNote()) }
                ?: AppResult.Error(DataError.Local.NOT_FOUND)
        }

    override suspend fun upsertNotes(notes: List<Note>): EmptyResult<DataError.Local> =
        safeDbCall { noteDao.upsertAll(notes.map { it.toNoteEntity() }) }
}
```

```kotlin
@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getById(id: String): NoteEntity?

    @Upsert
    suspend fun upsertAll(notes: List<NoteEntity>)
}
```

DAO rules: observation queries return `Flow` (not `suspend`); one-shot reads and writes are `suspend`; prefer `@Upsert` over `@Insert(onConflict = REPLACE)` (REPLACE deletes and re-inserts the row, which triggers `ON DELETE CASCADE` on child tables).

### Remote data source (Retrofit)

```kotlin
interface NoteApi {
    @GET("notes")
    suspend fun getNotes(): Response<List<NoteDto>>

    @POST("notes")
    suspend fun createNote(@Body note: NoteDto): Response<NoteDto>

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: String): Response<Unit>
}

class RetrofitNoteDataSource @Inject constructor(
    private val noteApi: NoteApi
) : NoteRemoteDataSource {

    override suspend fun fetchNotes(): AppResult<List<Note>, DataError.Network> =
        safeCall { noteApi.getNotes() }.map { dtos -> dtos.map { it.toNote() } }
}
```

Retrofit service functions always return `Response<T>` so `safeCall` can map HTTP status codes (see `error-handling`). The service is created in the feature's Hilt module:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NoteNetworkModule {
    @Provides
    @Singleton
    fun provideNoteApi(retrofit: Retrofit): NoteApi = retrofit.create(NoteApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteDataModule {
    @Binds abstract fun bindNoteLocalDataSource(impl: RoomNoteDataSource): NoteLocalDataSource
    @Binds abstract fun bindNoteRemoteDataSource(impl: RetrofitNoteDataSource): NoteRemoteDataSource
    // Unscoped: no state of its own (Room is the source of truth).
    // Scope it only when it holds a cache or a Mutex.
    @Binds abstract fun bindNoteRepository(impl: OfflineFirstNoteRepository): NoteRepository
}
```

### Repository (offline-first)

```kotlin
class OfflineFirstNoteRepository @Inject constructor(
    private val localDataSource: NoteLocalDataSource,
    private val remoteDataSource: NoteRemoteDataSource
) : NoteRepository {

    override fun observeNotes(): Flow<List<Note>> = localDataSource.observeNotes()

    override suspend fun refreshNotes(): EmptyResult<DataError> =
        remoteDataSource.fetchNotes()
            .flatMap { notes -> localDataSource.upsertNotes(notes) }
}
```

The UI observes Room; `refreshNotes()` only writes into Room and reports whether the refresh failed. A network failure never hides cached data.

Use names like `RoomNoteDataSource`, `RetrofitNoteDataSource`, `DataStoreSettingsDataSource`, `OfflineFirstNoteRepository`, `LocalWorkoutRepository` (Room only), `RemoteExerciseRepository` (network only). The name tells you what the class wraps or how it behaves.

---

## Network Setup (`core:data`)

Configure `Json`, `OkHttpClient`, and `Retrofit` once in `core:data` and provide them through Hilt:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .authenticator(tokenAuthenticator)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                    redactHeader("Authorization")
                })
            }
        }
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
}
```

Rules:
- `ignoreUnknownKeys = true` — a new field on the backend must not crash the app.
- Body-level logging only in debug builds, with `Authorization` redacted. Never log bodies in release.
- Base URL and API keys come from `BuildConfig` (fed from `local.properties`), never hardcoded.
- Converter: the official `retrofit2:converter-kotlinx-serialization`.

For tests, point Retrofit at `MockWebServer` (`mockWebServer.url("/")`) instead of swapping clients, with the production `Json` from `NetworkModule.provideJson()`.

---

## Authentication

> Types in this section (`AuthTokens`, `RefreshRequestDto`, `toAuthTokens()`, `RefreshTokenApi.refresh`, the session state flow) are illustrative — define them when auth is added.

If authentication is handled by a managed SDK (e.g. Firebase Auth), the SDK owns token storage and refresh — don't duplicate it. The `AuthInterceptor` just asks the SDK for the current ID token, and the `TokenAuthenticator` section below does not apply.

For a custom backend:

### Token storage

Store tokens in DataStore in `core:data` (or a dedicated `:core:auth` module if auth grows beyond a few classes). Encrypt the stored tokens with an Android Keystore-backed key; don't store them in plain text. Expose them through an interface:

```kotlin
interface TokenStorage {
    suspend fun getTokens(): AuthTokens?
    suspend fun saveTokens(tokens: AuthTokens)
    suspend fun clear()
}
```

### Attaching the token — `Interceptor`

```kotlin
class AuthInterceptor @Inject constructor(
    private val tokenStorage: TokenStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenStorage.getTokens()?.accessToken }
            ?: return chain.proceed(chain.request())
        return chain.proceed(
            chain.request().newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        )
    }
}
```

`runBlocking` is acceptable **only** inside OkHttp interceptors/authenticators — they run on OkHttp's background threads and the API is synchronous. Never use `runBlocking` anywhere else.

### Refreshing on 401 — `Authenticator`

```kotlin
class TokenAuthenticator @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val refreshApi: RefreshTokenApi
) : Authenticator {

    private val lock = Any()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount >= 2) return null // refresh already failed once

        synchronized(lock) {
            val current = runBlocking { tokenStorage.getTokens() } ?: return null
            val requestToken = response.request.header("Authorization")?.removePrefix("Bearer ")

            // Another request already refreshed the token while we waited for the lock
            if (requestToken != current.accessToken) {
                return response.request.withBearer(current.accessToken)
            }

            val refreshed = runBlocking {
                safeCall { refreshApi.refresh(RefreshRequestDto(current.refreshToken)) }
            }
            return when (refreshed) {
                is AppResult.Success -> {
                    val tokens = refreshed.data.toAuthTokens()
                    runBlocking { tokenStorage.saveTokens(tokens) }
                    response.request.withBearer(tokens.accessToken)
                }
                is AppResult.Error -> {
                    runBlocking { tokenStorage.clear() }
                    null
                }
            }
        }
    }
}

private val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()

private fun Request.withBearer(token: String): Request =
    newBuilder().header("Authorization", "Bearer $token").build()
```

Rules:
- `synchronized` + the token comparison prevent several parallel 401s from triggering several refreshes.
- `RefreshTokenApi` is built from a **separate** `Retrofit`/`OkHttpClient` (qualified with the custom `@AuthClient` qualifier — see `di-hilt` → Qualifiers; not `@Named`) without `AuthInterceptor` and `TokenAuthenticator` — otherwise a 401 from the refresh endpoint loops forever.
- On refresh failure, clear tokens and return `null`; the original call fails with `DataError.Network.UNAUTHORIZED`, and the app routes the user to login (e.g. by observing a session state flow).

---

## Room

The `@Database`, entities, DAOs, and migrations live in `:core:database` when shared (see `module-structure`). The database and DAOs are provided via Hilt from that module.

### Migrations

Prefer automatic migrations:

```kotlin
@Database(
    entities = [NoteEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase()
```

- Auto-migrations require `exportSchema = true` and a committed schema directory (configured by the `room` convention plugin). Schema JSON files are committed to git.
- Renames and deletions need an `AutoMigrationSpec` (`@RenameColumn`, `@DeleteColumn`, ...).
- Use manual `Migration` objects when the change is too complex for auto-migration (data transformation, table splits).
- Never ship `fallbackToDestructiveMigration()` in release builds — it silently wipes user data.
- Test migrations with `MigrationTestHelper`.

---

## Offline-First (when applicable)

Follow **Room as the single source of truth**: fetch from network → persist to Room → expose Room `Flow` to the ViewModel. The ViewModel never observes network responses directly.

- Reads: `observeX(): Flow<...>` from Room.
- Refresh: `refreshX(): EmptyResult<DataError>` — writes to Room, returns only success/failure.
- Writes: write to Room first, then sync to the network (immediately, or via WorkManager for guaranteed delivery when offline).

This pattern is optional — apply it when the feature requires offline support. Single-source features still expose a repository: e.g. `LocalWorkoutRepository` delegating to `RoomWorkoutDataSource`, or `RemoteExerciseRepository` delegating to `RetrofitExerciseDataSource`.

---

## Naming Conventions

| Thing | Convention | Example |
|---|---|---|
| Data source interface | `<Entity><Local/Remote>DataSource` | `NoteLocalDataSource`, `NoteRemoteDataSource` |
| Data source impl | what makes it unique | `RoomNoteDataSource`, `RetrofitNoteDataSource` |
| Repository interface | `<Entity>Repository` (every feature's data entry point) | `NoteRepository` |
| Repository impl | what makes it unique | `OfflineFirstNoteRepository`, `LocalWorkoutRepository` |
| Retrofit service | `<Entity>Api` | `NoteApi`, `RefreshTokenApi` |
| DTO | `<Model>Dto` | `NoteDto` |
| Room entity | `<Model>Entity` | `NoteEntity` |
| DAO | `<Model>Dao` | `NoteDao` |
| Mapper | extension fun on source type | `fun NoteDto.toNote()` |
| Hilt module | `<Feature><Purpose>Module` | `NoteDataModule`, `NoteNetworkModule` |

---

## Checklist: Adding a New Data Source or Repository

- [ ] Define domain model(s) in `feature:<name>:domain`
- [ ] Define the repository interface in `feature:<name>:domain` (always — even for a single source); data source interfaces in `feature:<name>:data`
- [ ] Define feature-specific error type(s) in `feature:<name>:domain` (implement `RootError`) — see `error-handling`
- [ ] Define DTOs (`@Serializable`, `@SerialName` where needed) and Room entities
- [ ] Write mappers as extension functions in `feature:<name>:data`
- [ ] Define the Retrofit service returning `Response<T>`; wrap calls in `safeCall`
- [ ] Wrap Room writes and one-shot reads in `safeDbCall`; observation queries return `Flow`
- [ ] Implement with `@Inject constructor`, named for what makes it unique (no `Impl`)
- [ ] Bind implementations with `@Binds` in a Hilt module in `feature:<name>:data`
- [ ] If the schema changed: bump the DB version, add a migration, commit the exported schema
