---
name: error-handling
description: |
  Typed error handling for Android - AppResult<D, E> wrapper, RootError, DataError, EmptyResult, map/mapError/flatMap/onSuccess/onFailure helpers, safeCall for Retrofit, safeDbCall for Room, CancellationException rules, Flow error strategy, and exhaustive UiText mapping. Use this skill whenever defining error types, creating or using the AppResult wrapper, writing a repository or data source that can fail, calling Retrofit or Room, catching exceptions, handling success/failure flows, mapping errors between layers, or working with typed errors anywhere in the app (not just the data layer — also validation, auth, domain logic). Trigger on phrases like "AppResult", "Result wrapper", "error handling", "DataError", "RootError", "onSuccess", "onFailure", "EmptyResult", "map result", "mapError", "safeCall", "try/catch", "exception", "error type", "validation error", "HTTP error", or "typed errors".
---

# Error Handling

## AppResult Wrapper (`core:domain`)

A generic, typed result type that works across all layers — data, domain, presentation, validation, anywhere a function can succeed or fail with a typed error.

```kotlin
interface RootError

sealed interface AppResult<out D, out E : RootError> {
    data class Success<out D>(val data: D) : AppResult<D, Nothing>
    data class Error<out E : RootError>(val error: E) : AppResult<Nothing, E>
}

typealias EmptyResult<E> = AppResult<Unit, E>
```

The wrapper is named `AppResult` (not `Result`) to avoid clashing with `kotlin.Result`. The marker interface is named `RootError` (not `Error`) to avoid clashing with the nested `AppResult.Error` class and with `kotlin.Error`.

### Import rule

The helper names `map`, `onSuccess`, and `onFailure` still coincide with `kotlin.Result` extensions. Therefore:
- Import the helpers explicitly from `core:domain`.
- Never use `runCatching` / `kotlin.Result` in app code — it catches `CancellationException` and returns untyped `Throwable`s. Use `safeCall` / `safeDbCall` (below) or an explicit `try/catch`.

---

## Extension Helpers (`core:domain`)

These live alongside the `AppResult` definition. All of them return `AppResult`, so they can be chained. They are `inline`, so suspend functions can be called inside their lambdas.

```kotlin
inline fun <T, E : RootError, R> AppResult<T, E>.map(
    transform: (T) -> R
): AppResult<R, E> = when (this) {
    is AppResult.Error -> this
    is AppResult.Success -> AppResult.Success(transform(data))
}

inline fun <T, E : RootError, F : RootError> AppResult<T, E>.mapError(
    transform: (E) -> F
): AppResult<T, F> = when (this) {
    is AppResult.Error -> AppResult.Error(transform(error))
    is AppResult.Success -> this
}

inline fun <T, E : RootError, R> AppResult<T, E>.flatMap(
    transform: (T) -> AppResult<R, E>
): AppResult<R, E> = when (this) {
    is AppResult.Error -> this
    is AppResult.Success -> transform(data)
}

inline fun <T, E : RootError> AppResult<T, E>.onSuccess(
    action: (T) -> Unit
): AppResult<T, E> {
    if (this is AppResult.Success) action(data)
    return this
}

inline fun <T, E : RootError> AppResult<T, E>.onFailure(
    action: (E) -> Unit
): AppResult<T, E> {
    if (this is AppResult.Error) action(error)
    return this
}

fun <T, E : RootError> AppResult<T, E>.asEmptyResult(): EmptyResult<E> = map { }
```

| Helper | Use when |
|---|---|
| `map` | Transform success data (DTO → domain model) |
| `mapError` | Translate an error into another layer's error type (`DataError` → `AuthError`) |
| `flatMap` | Chain a second operation that also returns `AppResult` (load → save → return) |
| `onSuccess` / `onFailure` | Side effects (update state, send event) without changing the AppResult |
| `asEmptyResult` | Drop success data when the caller only needs success/failure |

```kotlin
// Chaining without nested when (NoteRemoteDataSource / NoteLocalDataSource from `data-layer`)
suspend fun refreshNotes(): EmptyResult<DataError> =
    remoteDataSource.fetchNotes()
        .map { notes -> notes.filter { it.title.isNotBlank() } }
        .flatMap { notes -> localDataSource.upsertNotes(notes) }

// Translating errors at a layer boundary
suspend fun login(email: String, password: String): AppResult<User, AuthError> =
    authRepository.login(email, password).mapError { error ->
        when (error) {
            DataError.Network.UNAUTHORIZED -> AuthError.INVALID_CREDENTIALS
            DataError.Network.NO_INTERNET -> AuthError.NO_INTERNET
            else -> AuthError.UNKNOWN
        }
    }
```

`else` is acceptable inside `mapError` when collapsing many source errors into a smaller target type. It is **not** acceptable in `toUiText()` (see below).

---

## Shared Error Types (`core:domain`)

### DataError

```kotlin
sealed interface DataError : RootError {
    enum class Network : DataError {
        BAD_REQUEST,
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERVICE_UNAVAILABLE,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN
    }
}
```

### Feature-Specific Errors

Features define their own error types by implementing `RootError`:

```kotlin
enum class PasswordValidationError : RootError {
    TOO_SHORT,
    NO_UPPERCASE,
    NO_DIGIT
}

// Used with AppResult — always a single error, not a list:
fun validatePassword(pw: String): EmptyResult<PasswordValidationError>
```

Multiple validation errors are not supported — always return a single error type per AppResult.

---

## Exception Handling Philosophy

Never throw exceptions for expected failures — always return `AppResult.Error`. Catch exceptions at the layer that is responsible for them:

| Exception origin | Catch in | Example |
|---|---|---|
| HTTP / network | Data layer, via `safeCall` | `UnknownHostException` → `DataError.Network.NO_INTERNET` |
| Database / disk | Data layer, via `safeDbCall` | `SQLiteFullException` → `DataError.Local.DISK_FULL` |
| Business logic | Domain layer | Invalid input → `AppResult.Error(PasswordValidationError.TOO_SHORT)` |
| Platform / UI | Presentation layer | `ActivityNotFoundException` when launching an intent → show a snackbar |

The layer that owns the exception catches it and converts it to a typed `AppResult.Error`. Upper layers never see raw exceptions for expected failures. Unexpected exceptions (programming errors) are not caught — let them crash and surface in crash reporting.

### CancellationException — always rethrow

`catch (e: Exception)` inside a suspend function also catches `CancellationException`. Swallowing it breaks structured concurrency: a cancelled coroutine (e.g., `viewModelScope` after leaving the screen) keeps running and reports a bogus `UNKNOWN` error.

**Every** broad `catch` in suspend code must be preceded by:

```kotlin
catch (e: CancellationException) {
    throw e
}
```

`safeCall` and `safeDbCall` already do this — prefer them over hand-written `try/catch`.

---

## Data Layer Helpers (`core:data`)

### Retrofit: `safeCall`

Retrofit service functions return `Response<T>` so HTTP status codes can be mapped explicitly:

```kotlin
interface NoteApi {
    @GET("notes")
    suspend fun getNotes(): Response<List<NoteDto>>

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: String): Response<Unit>
}
```

```kotlin
inline fun <reified T> safeCall(execute: () -> Response<T>): AppResult<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: CancellationException) {
        throw e
    } catch (e: SocketTimeoutException) {
        return AppResult.Error(DataError.Network.REQUEST_TIMEOUT)
    } catch (e: UnknownHostException) {
        return AppResult.Error(DataError.Network.NO_INTERNET)
    } catch (e: IOException) {
        // ConnectException, SSL failures, dropped connections
        return AppResult.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        return AppResult.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        return AppResult.Error(DataError.Network.UNKNOWN)
    }
    return response.toAppResult()
}

inline fun <reified T> Response<T>.toAppResult(): AppResult<T, DataError.Network> {
    return when (code()) {
        in 200..299 -> {
            val body = body()
            when {
                body != null -> AppResult.Success(body)
                T::class == Unit::class -> {
                    @Suppress("UNCHECKED_CAST")
                    AppResult.Success(Unit as T)
                }
                else -> AppResult.Error(DataError.Network.SERIALIZATION)
            }
        }
        400 -> AppResult.Error(DataError.Network.BAD_REQUEST)
        401 -> AppResult.Error(DataError.Network.UNAUTHORIZED)
        403 -> AppResult.Error(DataError.Network.FORBIDDEN)
        404 -> AppResult.Error(DataError.Network.NOT_FOUND)
        408 -> AppResult.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> AppResult.Error(DataError.Network.CONFLICT)
        413 -> AppResult.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> AppResult.Error(DataError.Network.TOO_MANY_REQUESTS)
        503 -> AppResult.Error(DataError.Network.SERVICE_UNAVAILABLE)
        in 500..599 -> AppResult.Error(DataError.Network.SERVER_ERROR)
        else -> AppResult.Error(DataError.Network.UNKNOWN)
    }
}
```

Order of `catch` blocks matters: `SocketTimeoutException` is a subclass of `IOException` and must come first.

Usage:

```kotlin
class RetrofitNoteDataSource @Inject constructor(
    private val noteApi: NoteApi
) : NoteRemoteDataSource {
    override suspend fun fetchNotes(): AppResult<List<Note>, DataError.Network> =
        safeCall { noteApi.getNotes() }.map { dtos -> dtos.map { it.toNote() } }
}
```

### Room: `safeDbCall`

```kotlin
inline fun <T> safeDbCall(execute: () -> T): AppResult<T, DataError.Local> {
    return try {
        AppResult.Success(execute())
    } catch (e: CancellationException) {
        throw e
    } catch (e: SQLiteFullException) {
        AppResult.Error(DataError.Local.DISK_FULL)
    } catch (e: SQLiteException) {
        AppResult.Error(DataError.Local.UNKNOWN)
    }
}
```

Use it for Room **writes** and one-shot reads. Map "not found" explicitly from a null result, not from an exception:

```kotlin
override suspend fun getNote(id: String): AppResult<Note, DataError.Local> =
    safeDbCall { noteDao.getById(id) }
        .flatMap { entity ->
            entity?.let { AppResult.Success(it.toNote()) }
                ?: AppResult.Error(DataError.Local.NOT_FOUND)
        }
```

---

## Flow Error Strategy

Offline-first repositories split **observation** from **synchronization**:

| Operation | Return type | Why |
|---|---|---|
| Observe local data | `Flow<T>` (e.g., `Flow<List<Note>>`) | Room observation doesn't fail in expected ways; the DB is the source of truth |
| Refresh / sync from network | `suspend fun ...: EmptyResult<DataError>` | Network failure is expected and must be shown to the user |
| One-shot read / write | `suspend fun ...: AppResult<T, DataError.Local>` | Via `safeDbCall` |

```kotlin
interface NoteRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun refreshNotes(): EmptyResult<DataError>
}
```

The ViewModel collects `observeNotes()` into state and calls `refreshNotes()` separately, showing its error via `onFailure`.

Use `Flow<AppResult<T, E>>` only when a stream itself can emit expected failures (e.g., a socket or a polling network stream). In that case, convert exceptions with `.catch { emit(AppResult.Error(...)) }` inside the data layer — `Flow.catch` does not intercept cancellation, so no extra rethrow is needed there.

---

## Mapping Errors to UiText

Every error type that is displayed to the user should have a `.toUiText()` extension function. Place it in:

- **Feature's `presentation` module** — if the error is feature-specific (e.g., `AuthError.toUiText()`)
- **`core:presentation`** — if the error is shared across features (e.g., `DataError.toUiText()`)

If an error is purely internal and never shown to the user (e.g., a retry signal, an internal state marker), it does not need a `.toUiText()` mapping.

**Never use `else` in `toUiText()`.** List every case explicitly (several cases may map to the same string). That way, adding a new enum value produces a compile error until it is mapped, instead of silently showing "Unknown error".

```kotlin
// core:presentation
fun DataError.toUiText(): UiText {
    val resId = when (this) {
        DataError.Network.NO_INTERNET -> R.string.error_no_internet
        DataError.Network.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Network.UNAUTHORIZED -> R.string.error_unauthorized
        DataError.Network.FORBIDDEN -> R.string.error_forbidden
        DataError.Network.NOT_FOUND -> R.string.error_not_found
        DataError.Network.CONFLICT -> R.string.error_conflict
        DataError.Network.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.Network.PAYLOAD_TOO_LARGE -> R.string.error_payload_too_large
        DataError.Network.SERVER_ERROR,
        DataError.Network.SERVICE_UNAVAILABLE -> R.string.error_server
        DataError.Network.BAD_REQUEST,
        DataError.Network.SERIALIZATION,
        DataError.Network.UNKNOWN -> R.string.error_unknown
        DataError.Local.DISK_FULL -> R.string.error_disk_full
        DataError.Local.NOT_FOUND -> R.string.error_not_found
        DataError.Local.UNKNOWN -> R.string.error_unknown
    }
    return UiText.StringResource(resId)
}
```

---

## When to Use What

| Scenario | Error type | Example return |
|---|---|---|
| Network call | `DataError.Network` | `AppResult<List<NoteDto>, DataError.Network>` |
| Local DB access | `DataError.Local` | `AppResult<Note, DataError.Local>` |
| Repository combining sources | `DataError` (supertype) | `EmptyResult<DataError>` |
| Repository wrapping one source | That source's subtype | `AppResult<Workout, DataError.Local>` |
| Observing local data | none | `Flow<List<Note>>` |
| Domain validation | Custom `RootError` enum | `EmptyResult<PasswordValidationError>` |
| Auth logic | Custom `RootError` enum | `AppResult<User, AuthError>` |

The `AppResult` wrapper is not limited to the data layer — use it anywhere a function has typed success and failure outcomes.

---

## Checklist

- [ ] New error types implement `RootError` and live in `core:domain` (shared) or the feature's `domain` (feature-specific)
- [ ] Network calls go through `safeCall`, Room writes/one-shot reads through `safeDbCall`
- [ ] Every hand-written broad `catch` in suspend code rethrows `CancellationException` first
- [ ] No `runCatching` / `kotlin.Result`; `AppResult` helpers imported explicitly from `core:domain`
- [ ] Errors crossing into another layer's vocabulary use `mapError`
- [ ] User-facing errors have an exhaustive `toUiText()` with no `else`
