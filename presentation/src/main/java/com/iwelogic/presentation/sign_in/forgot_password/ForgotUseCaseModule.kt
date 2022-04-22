package com.iwelogic.presentation.sign_in.forgot_password


import com.iwelogic.domain.sign_in.forgot_password.ForgotPasswordRepository
import com.iwelogic.domain.sign_in.forgot_password.ForgotPasswordUseCase
import com.iwelogic.domain.sign_in.forgot_password.ForgotPasswordUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object ForgotUseCaseModule {

    @Provides
    fun provideForgotPasswordUseCase(forgotPasswordRepository: ForgotPasswordRepository): ForgotPasswordUseCase {
        return ForgotPasswordUseCaseImp(forgotPasswordRepository)
    }
}