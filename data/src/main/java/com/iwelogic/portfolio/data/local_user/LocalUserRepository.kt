package com.iwelogic.portfolio.data.local_user

import android.content.Context
import com.iwelogic.portfolio.data.models.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

interface LocalUserRepository {

    var userFlow: Flow<User>

    suspend fun updateUserPreference(user: User)
}

@InstallIn(SingletonComponent::class)
@Module
object LocalUserRepositoryModule {

    @Provides
    @Singleton
    fun provideLocalUserRepository(@ApplicationContext context: Context): LocalUserRepository {
        return LocalUserRepositoryImp(context)
    }
}