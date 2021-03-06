package com.iwelogic.presentation.ui.sign_in.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.sign_in.register.RegisterUseCase
import com.iwelogic.presentation.R
import com.iwelogic.presentation.models.SignIn
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.PopupData
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import com.iwelogic.presentation.ui.base.StringHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val stringHolder: StringHolder
) : BaseViewModel() {

    val image: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    val firstName: MutableLiveData<String> = MutableLiveData()
    val lastName: MutableLiveData<String> = MutableLiveData()
    val passwordOne: MutableLiveData<String> = MutableLiveData()
    val passwordTwo: MutableLiveData<String> = MutableLiveData()
    val emailError: MutableLiveData<String> = MutableLiveData()
    val passwordOneError: MutableLiveData<String> = MutableLiveData()
    val passwordTwoError: MutableLiveData<String> = MutableLiveData()
    val returnRegisteredUser: SingleLiveEvent<SignIn> = SingleLiveEvent()

    private val emailObserver: (String) -> Unit = {
        emailError.postValue(null)
    }

    private val passwordOneObserver: (String) -> Unit = {
        passwordOneError.postValue(null)
        passwordTwoError.postValue(null)
    }
    private val passwordTwoObserver: (String) -> Unit = {
        passwordOneError.postValue(null)
        passwordTwoError.postValue(null)
    }

    init {
        email.observeForever(emailObserver)
        passwordOne.observeForever(passwordOneObserver)
        passwordTwo.observeForever(passwordTwoObserver)
    }

    fun onClickRegister() {
        viewModelScope.launch {
            registerUseCase.register(email.value, firstName.value, lastName.value, passwordOne.value, passwordTwo.value).catch {
                showPopup.postValue(PopupData(text = it.message))
            }.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        progress.postValue(true)
                        hideKeyboard.postValue(true)
                    }
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> {
                        showPopup.postValue(
                            PopupData(
                                text = stringHolder.getString(R.string.need_confirm_email),
                                btnOkTitle = stringHolder.getString(R.string.confirmed),
                                btnOkCallBack = {
                                    returnRegisteredUser.postValue(SignIn(email.value, passwordOne.value))
                                    close.postValue(true)
                                })
                        )
                    }
                    is Result.Error -> when (result.code) {
                        Result.Error.Code.WRONG_EMAIL -> emailError.postValue(stringHolder.getString(R.string.wrong_email))
                        Result.Error.Code.PASSWORD_IS_TOO_SHORT -> passwordOneError.postValue(stringHolder.getString(R.string.password_is_too_short))
                        Result.Error.Code.PASSWORD_TWO_DOESNT_MATCH -> passwordTwoError.postValue(stringHolder.getString(R.string.password_does_not_match))
                        else -> showPopup.postValue(PopupData(text = result.message))
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        email.removeObserver(emailObserver)
        passwordOne.removeObserver(passwordOneObserver)
        passwordTwo.removeObserver(passwordTwoObserver)
    }
}