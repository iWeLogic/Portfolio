package com.iwelogic.portfolio.data.forgot_password

import com.iwelogic.portfolio.data.source.DataSource
import com.iwelogic.portfolio.domain.forgot_password.ForgotPasswordRepository
import com.iwelogic.portfolio.domain.register.RegisterRepository
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