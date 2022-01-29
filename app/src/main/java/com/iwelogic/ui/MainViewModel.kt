package com.iwelogic.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext applicationContext: Context) : ViewModel() {

    var navigator: MainNavigator? = null
    var context: WeakReference<Context> = WeakReference(applicationContext)

    fun checkIsLogged() {
        navigator?.openLogin()
    }
}