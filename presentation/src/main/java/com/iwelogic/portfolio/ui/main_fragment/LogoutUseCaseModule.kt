package com.iwelogic.portfolio.ui.main_fragment

import com.iwelogic.portfolio.domain.LocalUserRepository
import com.iwelogic.portfolio.domain.main_fragment.LogoutUseCase
import com.iwelogic.portfolio.domain.main_fragment.LogoutUseCaseImp
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