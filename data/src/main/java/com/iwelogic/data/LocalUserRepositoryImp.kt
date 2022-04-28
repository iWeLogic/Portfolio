package com.iwelogic.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.codelab.android.datastore.UserPreferences
import com.iwelogic.domain.LocalUserRepository
import com.iwelogic.domain.models.UserDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class LocalUserRepositoryImp(private val context: Context) : LocalUserRepository {

    private val Context.userPreferencesStore: DataStore<UserPreferences> by dataStore(fileName = "settings", serializer = UserPreferencesSerializer)

    override val userFlow: Flow<UserDomain>
        get() = context.userPreferencesStore.data.catch {
            if (it is IOException) {
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw it
            }
        }.map {
            UserDomain(
                objectId = if(it.objectId.isEmpty()) null else it.objectId,
                firstName = if(it.firstName.isEmpty()) null else it.firstName,
                lastName =if(it.lastName.isEmpty()) null else it.lastName,
                image = if(it.image.isEmpty()) null else it.image,
                email = if(it.email.isEmpty()) null else it.email,
                userToken = if(it.userToken.isEmpty()) null else it.userToken,
            )
        }

    override suspend fun updateUserPreference(user: UserDomain) {
        context.userPreferencesStore.updateData { preferences ->
            val builder = preferences.toBuilder()
            user.firstName?.let { builder.setFirstName(it) }
            user.lastName?.let { builder.setLastName(it) }
            user.image?.let { builder.setImage(it) }
            user.objectId?.let { builder.setObjectId(it) }
            user.email?.let { builder.setEmail(it) }
            user.userToken?.let { builder.setUserToken(it) }
            builder.build()
        }
    }

    override suspend fun clearData() {
        context.userPreferencesStore.updateData { preferences ->
            val builder = preferences.toBuilder()
            builder.userToken = ""
            builder.objectId = ""
            builder.build()
        }
    }
}