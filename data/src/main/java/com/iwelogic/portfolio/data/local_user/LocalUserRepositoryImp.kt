package com.iwelogic.portfolio.data.local_user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.codelab.android.datastore.UserPreferences
import com.iwelogic.portfolio.data.UserPreferencesSerializer
import com.iwelogic.portfolio.domain.main.models.User
import com.iwelogic.portfolio.domain.main.main.LocalUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class LocalUserRepositoryImp(val context: Context) : LocalUserRepository {

    private val Context.userPreferencesStore: DataStore<UserPreferences> by dataStore(fileName = "settings", serializer = UserPreferencesSerializer)

    override var userFlow: Flow<User>
        get() = context.userPreferencesStore.data.catch {
            if (it is IOException) {
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw it
            }
        }.map { User(it.name, it.userToken, it.email) }
        set(value) {}

    override suspend fun updateUserPreference(user: User) {
        context.userPreferencesStore.updateData { preferences ->
            val builder = preferences.toBuilder()
            user.name?.let { builder.setName(it) }
            user.email?.let { builder.setEmail(it) }
            user.userToken?.let { builder.setUserToken(it) }
            builder.build()
        }
    }
}