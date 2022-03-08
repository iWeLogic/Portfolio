package com.iwelogic.portfolio.domain.main.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface UserExistUseCase {

    suspend fun checkIsUserExist() : ExistStatus
}

sealed class ExistStatus {

    object True : ExistStatus()

    object False : ExistStatus()
}

@InstallIn(ViewModelComponent::class)
@Module
object UserExistUseCaseModule {

    @Provides
    fun provideUserExistUseCase(localUserRepository: LocalUserRepository): UserExistUseCase {
        return UserExistUseCaseImp(localUserRepository)
    }
}