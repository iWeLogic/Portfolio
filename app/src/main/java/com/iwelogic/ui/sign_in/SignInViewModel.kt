package com.iwelogic.ui.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.iwelogic.data.Result
import com.iwelogic.data.repository.RepositoryImpl
import com.iwelogic.ui.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignInViewModel @AssistedInject constructor(@Assisted private val repository: RepositoryImpl) : BaseViewModel<SignInNavigator>() {

    companion object {
        fun provideFactory(assistedFactory: SignInViewModelFactory, repository: RepositoryImpl): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(repository) as T
            }
        }
    }

    fun onClickRegister() {
        navigator?.openRegister()
    }

    fun onClickLogin() {
        viewModelScope.launch {
            repository.register("novaknazar2@gmail.com", "kleo2304").collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> {
                        Log.w("myLog", "onClickLogin: "  + result.data?.userStatus)
                    }
                    is Result.Error -> {
                        navigator?.showToast(result.message)
                    }
                }
            }
        }
    }
}

@AssistedFactory
interface SignInViewModelFactory {
    fun create(repository: RepositoryImpl): SignInViewModel
}

