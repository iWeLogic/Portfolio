package com.iwelogic.data

import android.content.Context
import com.iwelogic.data.store.LocalStorage
import com.iwelogic.data.store.LocalStorageImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStorageModule {

    @Provides
    @Singleton
    fun provideApi(@ApplicationContext context: Context): LocalStorage {
        return LocalStorageImp(context)
    }
}
