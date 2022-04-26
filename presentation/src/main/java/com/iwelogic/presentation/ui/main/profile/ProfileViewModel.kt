package com.iwelogic.presentation.ui.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.main.profile.LogoutUseCase
import com.iwelogic.domain.main.profile.UserUseCase
import com.iwelogic.domain.models.Result
import com.iwelogic.presentation.R
import com.iwelogic.presentation.models.UserPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.PopupData
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import com.iwelogic.presentation.ui.base.StringHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val userUseCase: UserUseCase,
    private val stringHolder: StringHolder,
    private val mapper: UserDomainPresentationMapper
) : BaseViewModel() {

    var user: MutableLiveData<UserPresentation> = MutableLiveData()
    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun onClickLogout() {
        viewModelScope.launch {
            logoutUseCase.logout().collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> {
                        user.postValue(null)
                        close.postValue(true)
                    }
                    is Result.Error -> showPopup.postValue(PopupData(text = result.message))
                }
            }
        }
    }

    override fun onReload() {
        user.value?.objectId?.let { objectId ->
            viewModelScope.launch {
                userUseCase.getUser(objectId).collect { result ->
                    when (result) {
                        is Result.Loading -> progress.postValue(true)
                        is Result.Finish -> progress.postValue(false)
                        is Result.Success -> user.postValue(result.data?.let { mapper.map(it) })
                        is Result.Error -> showPopup.postValue(PopupData(text = result.message))
                    }
                }
            }
        }
    }

    fun onClickSave() {
        user.value?.let { user ->
            viewModelScope.launch {
                userUseCase.update(mapper.reverseMap(user)).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            hideKeyboard.postValue(true)
                            progress.postValue(true)
                        }
                        is Result.Finish -> progress.postValue(false)
                        is Result.Success -> showToast.postValue(stringHolder.getString(R.string.user_updated))
                        is Result.Error -> showPopup.postValue(PopupData(text = result.message))
                    }
                }
            }
        }
    }

    fun onClickLogin() {
        openLogin.postValue(true)
    }
}
