package com.iwelogic.presentation.sign_in.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.models.DomainRegister
import com.iwelogic.domain.models.Result
import com.iwelogic.domain.sign_in.register.RegisterUseCase
import com.iwelogic.presentation.base.BaseViewModel
import com.iwelogic.presentation.base.SingleLiveEvent
import com.iwelogic.presentation.models.SignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(var registerUseCase: RegisterUseCase) : BaseViewModel() {

    val image: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData("novaknazar@gmail.com")
    val firstName: MutableLiveData<String> = MutableLiveData("Nazar")
    val lastName: MutableLiveData<String> = MutableLiveData("Novak")
    val passwordOne: MutableLiveData<String> = MutableLiveData("kleo2304")
    val passwordTwo: MutableLiveData<String> = MutableLiveData("kleo2304")
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
            registerUseCase.register(DomainRegister(email.value, image.value, firstName.value, lastName.value, passwordOne.value, passwordTwo.value)).catch {
                error.postValue(it.message)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> {
                        returnRegisteredUser.postValue(SignIn(email.value, passwordOne.value))
                        close.postValue(true)
                    }
                    is Result.Error -> {
                        when (result.code) {
                            Result.Error.Code.NOT_CONFIRMED -> {
                                // navigator?.showWarningDialog(result.message)
                            }
                            Result.Error.Code.WRONG_EMAIL_OR_PASSWORD -> {
                                //passwordError.postValue(result.message)
                            }
                            else -> {
                                //navigator?.showToast(result.message)
                            }
                        }
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