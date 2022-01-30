package com.iwelogic.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iwelogic.data.store.LocalStorageImp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext applicationContext: Context, val localStorage: LocalStorageImp) : ViewModel() {

    var navigator: MainNavigator? = null
    var context: WeakReference<Context> = WeakReference(applicationContext)

    fun checkIsLogged() {
        viewModelScope.launch {
            localStorage.userFlow.collect {
                if (it.userToken.isNullOrEmpty()) {
                    navigator?.openLogin()
                } else {
                    navigator?.openMain()
                }
            }
        }
    }
}