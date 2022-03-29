package com.iwelogic.portfolio.presentation.sign_in.register

import android.content.Context
import com.iwelogic.portfolio.domain.sign_in.register.RegisterRepository
import com.iwelogic.portfolio.domain.sign_in.register.RegisterUseCase
import com.iwelogic.portfolio.domain.sign_in.register.RegisterUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
object RegisterUseCaseModule {

    @Provides
    fun provideRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCaseImp(registerRepository)
    }
}