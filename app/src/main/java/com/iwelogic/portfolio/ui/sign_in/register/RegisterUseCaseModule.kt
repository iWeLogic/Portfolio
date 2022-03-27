package com.iwelogic.portfolio.ui.sign_in.register

import com.iwelogic.portfolio.domain.register.RegisterRepository
import com.iwelogic.portfolio.domain.register.RegisterUseCase
import com.iwelogic.portfolio.domain.register.RegisterUseCaseImp
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