package com.iwelogic.portfolio.data.sign_in.login

import com.iwelogic.portfolio.data.sign_in.login.LoginRepositoryImp
import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.sign_in.login.LoginRepository
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
        return LoginRepositoryImp(dataSource)
    }
}