package com.iwelogic.portfolio.presentation.sign_in.forgot_password

import com.iwelogic.portfolio.presentation.source.DataSource
import com.iwelogic.portfolio.domain.sign_in.forgot_password.ForgotPasswordRepository
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
    fun provideRegisterRepository(dataSource: DataSource): ForgotPasswordRepository {
        return ForgotPasswordRepositoryImp(dataSource)
    }
}