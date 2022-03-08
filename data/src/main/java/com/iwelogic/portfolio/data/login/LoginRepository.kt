package com.iwelogic.portfolio.data.login

import com.iwelogic.portfolio.data.models.Result
import com.iwelogic.portfolio.data.models.SignInData
import com.iwelogic.portfolio.data.models.User
import com.iwelogic.portfolio.data.source.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

interface LoginRepository {
    fun login(data: SignInData): Flow<Result<User>>
}

@InstallIn(SingletonComponent::class)
@Module
object LoginRepositoryModule {

    @Provides
    @Singleton
    fun provideLocalUserRepository(dataSource: DataSource): LoginRepository {
        return LoginRepositoryImp(dataSource)
    }
}