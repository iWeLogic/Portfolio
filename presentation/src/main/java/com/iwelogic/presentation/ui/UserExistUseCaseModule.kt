package com.iwelogic.presentation.ui

import com.iwelogic.domain.LocalUserRepository
import com.iwelogic.domain.UserExistUseCase
import com.iwelogic.domain.UserExistUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object UserExistUseCaseModule {

    @Provides
    fun provideUserExistUseCase(localUserRepository: LocalUserRepository): UserExistUseCase {
        return UserExistUseCaseImp(localUserRepository)
    }
}