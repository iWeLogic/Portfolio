package com.iwelogic.data.store

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalStorageModule {

    @Provides
    @Singleton
    fun provideLocalStorage(@ApplicationContext appContext: Context): DataStorageRepositoryImp {
        return DataStorageRepositoryImp(appContext)
    }
}