package com.iwelogic.portfolio.presentation.main.profile

import com.iwelogic.portfolio.domain.LocalUserRepository
import com.iwelogic.portfolio.domain.main.profile.LogoutUseCase
import com.iwelogic.portfolio.domain.main.profile.LogoutUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object LogoutUseCaseModule {

    @Provides
    fun provideLogoutUseCase(localUserRepository: LocalUserRepository): LogoutUseCase {
        return LogoutUseCaseImp(localUserRepository)
    }
}