package com.iwelogic.portfolio.ui.register

import androidx.lifecycle.viewModelScope
import com.iwelogic.portfolio.domain.models.RegisterData
import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.domain.register.RegisterUseCase
import com.iwelogic.portfolio.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(var registerUseCase: RegisterUseCase) : BaseViewModel() {

    init {
        register()
    }

    fun register() {
        viewModelScope.launch {
            registerUseCase.register(RegisterData("novaknazar@gmail.com", "kleo2304")).catch {

            }.collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> {
                        //openMain.postValue(true)
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
}