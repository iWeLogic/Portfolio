package com.iwelogic.portfolio.ui.main.apps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.portfolio.domain.apps.AppsUseCase
import com.iwelogic.portfolio.domain.models.App
import com.iwelogic.portfolio.domain.models.Result
import com.iwelogic.portfolio.ui.base.BaseViewModel
import com.iwelogic.portfolio.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(private val appsUseCase: AppsUseCase) : BaseViewModel() {

    val openDetails: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val apps: MutableLiveData<List<App>> = MutableLiveData(ArrayList())

    init {
        onReload()
    }

    val onClick: (App) -> Unit = {

    }

    override fun onReload() {
        error.postValue(null)
        apps.postValue(ArrayList())
        viewModelScope.launch {
            appsUseCase.getApps().catch {
                error.postValue(it.message)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> apps.postValue(result.data)
                    is Result.Error -> error.postValue(result.message)
                }
            }
        }
    }
}