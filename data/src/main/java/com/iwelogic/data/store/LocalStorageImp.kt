package com.iwelogic.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.codelab.android.datastore.UserPreferences
import com.iwelogic.data.UserPreferencesSerializer
import com.iwelogic.data.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class LocalStorageImp constructor(val context: Context) : LocalStorage {

    private val Context.userPreferencesStore: DataStore<UserPreferences> by dataStore(fileName = "settings", serializer = UserPreferencesSerializer)

    val userFlow: Flow<User> = context.userPreferencesStore.data.catch {
        if (it is IOException) {
            emit(UserPreferences.getDefaultInstance())
        } else {
            throw it
        }
    }.map { User(it.name, it.userToken, it.email) }

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