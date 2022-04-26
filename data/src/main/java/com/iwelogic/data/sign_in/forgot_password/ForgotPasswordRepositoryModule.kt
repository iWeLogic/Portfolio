package com.iwelogic.data.sign_in.forgot_password

import com.iwelogic.data.source.DataSource
import com.iwelogic.domain.sign_in.forgot_password.ForgotPasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ForgotPasswordRepositoryModule {

    @Provides
    @Singleton
    fun provide(dataSource: DataSource): ForgotPasswordRepository {
        return ForgotPasswordRepositoryImp(dataSource)
    }
}