package com.iwelogic.portfolio.domain.main_fragment

import com.iwelogic.portfolio.data.local_user.LocalUserRepository
import com.iwelogic.portfolio.data.models.Result
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface LogoutUseCase {

    fun logout(): Flow<Result<Any>>
}

@InstallIn(ViewModelComponent::class)
@Module
object LogoutUseCaseModule {

    @Provides
    fun provideLogoutUseCase(localUserRepository: LocalUserRepository): LogoutUseCase {
        return LogoutUseCaseImp(localUserRepository)
    }
}