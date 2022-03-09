package com.iwelogic.portfolio.data.local_user

import android.content.Context
import com.iwelogic.portfolio.domain.LocalUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalUserRepositoryModule {

    @Provides
    @Singleton
    fun provideLocalUserRepository(@ApplicationContext context: Context): LocalUserRepository {
        return LocalUserRepositoryImp(context)
    }
}