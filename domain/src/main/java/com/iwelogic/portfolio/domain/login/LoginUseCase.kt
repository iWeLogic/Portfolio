package com.iwelogic.portfolio.domain.login

import com.iwelogic.portfolio.data.local_user.LocalUserRepository
import com.iwelogic.portfolio.data.login.LoginRepository
import com.iwelogic.portfolio.data.models.Result
import com.iwelogic.portfolio.data.models.SignInData
import com.iwelogic.portfolio.data.models.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun login(data: SignInData): Flow<Result<User>>
}

@InstallIn(ViewModelComponent::class)
@Module
object LoginUseCaseModule {

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository, localUserRepository: LocalUserRepository): LoginUseCase {
        return LoginUseCaseImp(loginRepository, localUserRepository)
    }
}