package com.iwelogic.presentation.ui

import com.iwelogic.domain.LocalUserRepository
import com.iwelogic.domain.LocalUserUseCase
import com.iwelogic.domain.LocalUserUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object UserExistUseCaseModule {

    @Provides
    fun provideUserExistUseCase(localUserRepository: LocalUserRepository): LocalUserUseCase {
        return LocalUserUseCaseImp(localUserRepository)
    }
}