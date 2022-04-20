package com.iwelogic.presentation.main.apps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.presentation.models.App
import com.iwelogic.domain.main.apps.AppsUseCase
import com.iwelogic.domain.models.Result
import com.iwelogic.presentation.base.BaseViewModel
import com.iwelogic.presentation.base.SingleLiveEvent
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
                    is Result.Success -> apps.postValue(result.data?.map {
                        App(id = it.id, icon = it.icon, title = it.title, description = it.description)
                    })
                    is Result.Error -> error.postValue(result.message)
                }
            }
        }
    }
}
