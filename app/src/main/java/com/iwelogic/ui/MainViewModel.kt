package com.iwelogic.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.data.store.LocalStorageImp
import com.iwelogic.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext applicationContext: Context, val localStorage: LocalStorageImp) : ViewModel() {

    var context: WeakReference<Context> = WeakReference(applicationContext)
    val openMain: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun checkIsLogged() {
        viewModelScope.launch {
            localStorage.userFlow.collect {
                if (it.userToken.isNullOrEmpty()) {
                    openLogin.postValue(true)
                } else {
                    openMain.postValue(true)
                }
            }
        }
    }
}