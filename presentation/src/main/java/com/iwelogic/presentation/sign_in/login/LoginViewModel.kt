package com.iwelogic.presentation.sign_in.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.sign_in.login.LoginUseCase
import com.iwelogic.presentation.R
import com.iwelogic.presentation.base.BaseViewModel
import com.iwelogic.presentation.base.PopupData
import com.iwelogic.presentation.base.SingleLiveEvent
import com.iwelogic.presentation.base.StringHolder
import com.iwelogic.presentation.models.SignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase, private val stringHolder: StringHolder) : BaseViewModel() {

    val openMain: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val openRegister: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val openForgotPassword: SingleLiveEvent<String> = SingleLiveEvent()
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    val emailError: MutableLiveData<String> = MutableLiveData()
    val passwordError: MutableLiveData<String> = MutableLiveData()
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

    fun loginWithRegisteredUser(signIn: SignIn?) {
        signIn?.let {
            password.value = signIn.password
            email.value = signIn.login
            password.postValue(password.value)
            email.postValue(email.value)
            login()
        }
    }

    fun onClickSignIn() {
        login()
    }

    private fun login() {
        viewModelScope.launch {
            loginUseCase.login(email.value, password.value).catch {
                showPopup.postValue(PopupData(text = it.message))
            }.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        progress.postValue(true)
                        hideKeyboard.postValue(true)
                    }
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> openMain.postValue(true)
                    is Result.Error -> when (result.code) {
                        Result.Error.Code.NOT_CONFIRMED -> showPopup.postValue(
                            PopupData(
                                text = result.message,
                                btnOkTitle = stringHolder.getString(R.string.retry),
                                btnCancelTitle = stringHolder.getString(R.string.cancel),
                                btnOkCallBack = { login() })
                        )
                        Result.Error.Code.INVALID_CREDENTIALS -> passwordError.postValue(result.message)
                        Result.Error.Code.WRONG_EMAIL -> emailError.postValue(stringHolder.getString(R.string.wrong_email))
                        Result.Error.Code.WRONG_PASSWORD -> passwordError.postValue(stringHolder.getString(R.string.wrong_password))
                        else -> showPopup.postValue(PopupData(result.message))
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        email.removeObserver(emailObserver)
        password.removeObserver(passwordObserver)
    }
}
