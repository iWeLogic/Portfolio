package com.iwelogic.presentation.sign_in.register

import com.iwelogic.domain.sign_in.register.RegisterRepository
import com.iwelogic.domain.sign_in.register.RegisterUseCase
import com.iwelogic.domain.sign_in.register.RegisterUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object RegisterUseCaseModule {

    @Provides
    fun provideRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCaseImp(registerRepository)
    }
}