package com.iwelogic.portfolio.ui.profile

import com.iwelogic.portfolio.domain.LocalUserRepository
import com.iwelogic.portfolio.domain.profile.LogoutUseCase
import com.iwelogic.portfolio.domain.profile.LogoutUseCaseImp
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