package com.iwelogic.data.sign_in.login

import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.sign_in.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LoginRepositoryModule {

    @Provides
    @Singleton
    fun provideLocalUserRepository(dataSource: DataSource): LoginRepository {
        return LoginRepositoryImp(dataSource, UserDataDomainMapper())
    }
}