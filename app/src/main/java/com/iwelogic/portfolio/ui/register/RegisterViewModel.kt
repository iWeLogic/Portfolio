package com.iwelogic.portfolio.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.portfolio.data.repository.Repository
import com.iwelogic.portfolio.ui.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class RegisterViewModel @AssistedInject constructor(@Assisted private val repository: Repository) : BaseViewModel() {

    companion object {
        fun provideFactory(assistedFactory: RegisterViewModelFactory, repository: Repository): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(repository) as T
            }
        }
    }
}

@AssistedFactory
interface RegisterViewModelFactory {
    fun create(repository: Repository): RegisterViewModel
}
