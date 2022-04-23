package com.iwelogic.presentation.ui.sign_in.forgot_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.sign_in.forgot_password.ForgotPasswordUseCase
import com.iwelogic.presentation.R
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.PopupData
import com.iwelogic.presentation.ui.base.StringHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val forgotPasswordUseCase: ForgotPasswordUseCase, private val stringHolder: StringHolder) : BaseViewModel() {

    var email: MutableLiveData<String> = MutableLiveData()
    val emailError: MutableLiveData<String> = MutableLiveData()

    private val emailObserver: (String) -> Unit = {
        emailError.postValue(null)
    }

    init {
        email.observeForever(emailObserver)
    }

    fun onClickRemember() {
        viewModelScope.launch {
            forgotPasswordUseCase.remember(email.value).catch {
                showPopup.postValue(PopupData(text = it.message))
            }.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        progress.postValue(true)
                        hideKeyboard.postValue(true)
                    }
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> showPopup.postValue(PopupData(
                        text = stringHolder.getString(R.string.new_password_was_sent),
                        btnOkCallBack = {
                            close.postValue(true)
                        }
                    ))
                    is Result.Error -> when (result.code) {
                        Result.Error.Code.WRONG_EMAIL -> emailError.postValue(stringHolder.getString(R.string.wrong_email))
                        else -> showPopup.postValue(PopupData(result.message))
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        email.removeObserver(emailObserver)
    }
}