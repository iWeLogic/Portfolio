package com.iwelogic.portfolio.presentation.sign_in.login


import com.iwelogic.portfolio.domain.LocalUserRepository
import com.iwelogic.portfolio.domain.sign_in.login.LoginRepository
import com.iwelogic.portfolio.domain.sign_in.login.LoginUseCase
import com.iwelogic.portfolio.domain.sign_in.login.LoginUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object LoginUseCaseModule {

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository, localUserRepository: LocalUserRepository): LoginUseCase {
        return LoginUseCaseImp(loginRepository, localUserRepository)
    }
}