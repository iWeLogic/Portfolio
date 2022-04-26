package com.iwelogic.data.main.profile

import com.iwelogic.data.sign_in.login.UserDataDomainMapper
import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.main.profile.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UserRepositoryModule {

    @Provides
    @Singleton
    fun provide(dataSource: DataSource): UserRepository {
        return UserRepositoryImp(dataSource, UserDataDomainMapper())
    }
}