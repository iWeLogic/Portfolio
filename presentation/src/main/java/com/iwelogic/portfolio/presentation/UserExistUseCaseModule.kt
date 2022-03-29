package com.iwelogic.portfolio.presentation

import com.iwelogic.portfolio.domain.LocalUserRepository
import com.iwelogic.portfolio.domain.UserExistUseCase
import com.iwelogic.portfolio.domain.UserExistUseCaseImp
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