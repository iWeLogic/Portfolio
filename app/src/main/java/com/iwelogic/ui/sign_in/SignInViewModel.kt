package com.iwelogic.ui.sign_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.iwelogic.R
import com.iwelogic.data.models.SignInData
import com.iwelogic.data.repository.RepositoryImp
import com.iwelogic.data.store.DataStorageRepositoryImp
import com.iwelogic.ui.base.BaseViewModel
import com.iwelogic.ui.base.SingleLiveEvent
import com.iwelogic.utils.isEmail
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel @AssistedInject constructor(@Assisted private val repository: RepositoryImp, @Assisted private val localStorage: DataStorageRepositoryImp) : BaseViewModel() {

    companion object {
        fun provideFactory(assistedFactory: SignInViewModelFactory, repository: RepositoryImp, localStorage: DataStorageRepositoryImp): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(repository, localStorage) as T
            }
        }
    }

    val openMain: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val openRegister: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val openForgotPassword: SingleLiveEvent<String> = SingleLiveEvent()
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
        openRegister.postValue(true)
    }

    fun onClickForgotPassword() {
        openForgotPassword.postValue(email.value)
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
                    is com.iwelogic.data.Result.Loading -> progress.postValue(true)
                    is com.iwelogic.data.Result.Finish -> progress.postValue(false)
                    is com.iwelogic.data.Result.Success -> {
                        withContext(Dispatchers.IO) {
                            result.data?.let {
                                localStorage.updateUserPreference(it)
                                openMain.postValue(true)
                            }
                        }
                    }
                    is com.iwelogic.data.Result.Error -> {
                        when (result.code) {
                            com.iwelogic.data.Result.Error.Code.NOT_CONFIRMED -> {
                                // navigator?.showWarningDialog(result.message)
                            }
                            com.iwelogic.data.Result.Error.Code.WRONG_EMAIL_OR_PASSWORD -> passwordError.postValue(result.message)
                            else -> {
                                //navigator?.showToast(result.message)
                            }
                        }
                    }
                }
            }
        }
    }
}

@AssistedFactory
interface SignInViewModelFactory {
    fun create(repository: RepositoryImp, localStorage: DataStorageRepositoryImp): SignInViewModel
}

