package com.iwelogic.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.codelab.android.datastore.UserPreferences
import com.iwelogic.data.UserPreferencesSerializer
import com.iwelogic.data.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class LocalStorageImp (val context: Context) : LocalStorage {

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

    suspend fun updateUserPreference(user: User) {
        context.userPreferencesStore.updateData { preferences ->
            val builder = preferences.toBuilder()
            user.name?.let { builder.setName(it) }
            user.email?.let { builder.setEmail(it) }
            user.userToken?.let { builder.setUserToken(it) }
            builder.build()
        }
    }
}