package com.iwelogic.ui.sign_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.iwelogic.R
import com.iwelogic.data.Result
import com.iwelogic.data.repository.RepositoryImp
import com.iwelogic.data.store.LocalStorageImp
import com.iwelogic.models.SignInData
import com.iwelogic.ui.base.BaseViewModel
import com.iwelogic.utils.isEmail
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel @AssistedInject constructor(@Assisted private val repository: RepositoryImp, @Assisted private val localStorage: LocalStorageImp) : BaseViewModel<SignInNavigator>() {

    companion object {
        fun provideFactory(assistedFactory: SignInViewModelFactory, repository: RepositoryImp, localStorage: LocalStorageImp): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(repository, localStorage) as T
            }
        }
    }

    var email: MutableLiveData<String> = MutableLiveData("novaknazar@gmail.com")
    var password: MutableLiveData<String> = MutableLiveData("kleo2304")
    val emailError: MutableLiveData<Any> = MutableLiveData()
    val passwordError: MutableLiveData<Any> = MutableLiveData()
    private val emailObserver: (String) -> Unit = {
        passwordError.postValue(null)
        emailError.postValue(null)
    }
    private val passwordObserver: (String) -> Unit = {
        passwordError.postValue(null)
        emailError.postValue(null)
    }

    init {
        email.observeForever(emailObserver)
        password.observeForever(passwordObserver)
    }

    fun onClickRegister() {
        navigator?.openRegister()
    }

    fun onClickForgotPassword() {
        navigator?.openForgotPassword(email.value)
    }

    override fun onCleared() {
        super.onCleared()
        email.removeObserver(emailObserver)
        password.removeObserver(passwordObserver)
    }

    fun onClickSignIn() {
        var allFieldsCorrect = true
        if (!email.value.isEmail()) {
            emailError.postValue(R.string.wrong_email)
            allFieldsCorrect = false
        }
        if (password.value.isNullOrEmpty() || password.value!!.length < 8) {
            passwordError.postValue(R.string.wrong_password)
            allFieldsCorrect = false
        }
        if (!allFieldsCorrect) return

        viewModelScope.launch {
            repository.login(SignInData(email.value, password.value)).collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> {
                        withContext(Dispatchers.IO){
                            result.data?.let {
                                localStorage.updateUserPreference(it)
                                navigator?.openMain()
                            }
                        }
                    }
                    is Result.Error -> {
                        when (result.code) {
                            Result.Error.Code.NOT_CONFIRMED -> navigator?.showWarningDialog(result.message)
                            Result.Error.Code.WRONG_EMAIL_OR_PASSWORD -> passwordError.postValue(result.message)
                            else -> navigator?.showToast(result.message)
                        }
                    }
                }
            }
        }
    }
}

@AssistedFactory
interface SignInViewModelFactory {
    fun create(repository: RepositoryImp, localStorage: LocalStorageImp): SignInViewModel
}

