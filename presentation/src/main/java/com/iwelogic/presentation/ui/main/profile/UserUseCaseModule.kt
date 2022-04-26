package com.iwelogic.presentation.ui.main.profile

import com.iwelogic.domain.LocalUserRepository
import com.iwelogic.domain.main.profile.UserRepository
import com.iwelogic.domain.main.profile.UserUseCase
import com.iwelogic.domain.main.profile.UserUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object UserUseCaseModule {

    @Provides
    fun provideUserUseCase(userRepository: UserRepository, localUserRepository: LocalUserRepository): UserUseCase {
        return UserUseCaseImp(userRepository, localUserRepository)
    }
}