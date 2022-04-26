package com.iwelogic.presentation.ui.main.profile

import com.iwelogic.domain.LocalUserRepository
import com.iwelogic.domain.main.profile.LogoutUseCase
import com.iwelogic.domain.main.profile.LogoutUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object LogoutUseCaseModule {

    @Provides
    fun provide(localUserRepository: LocalUserRepository): LogoutUseCase {
        return LogoutUseCaseImp(localUserRepository)
    }
}