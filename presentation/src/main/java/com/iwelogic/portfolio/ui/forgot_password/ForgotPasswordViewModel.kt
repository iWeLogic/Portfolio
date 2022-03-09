package com.iwelogic.portfolio.ui.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.portfolio.data.register.RegisterRepository
import com.iwelogic.portfolio.ui.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ForgotPasswordViewModel @AssistedInject constructor(@Assisted private val registerRepository: RegisterRepository) : BaseViewModel() {

    companion object {
        fun provideFactory(assistedFactory: ForgotPasswordViewModelFactory, registerRepository: RegisterRepository): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(registerRepository) as T
            }
        }
    }
}

@AssistedFactory
interface ForgotPasswordViewModelFactory {
    fun create(registerRepository: RegisterRepository): ForgotPasswordViewModel
}
