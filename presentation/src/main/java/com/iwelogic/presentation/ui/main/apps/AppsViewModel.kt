package com.iwelogic.presentation.ui.main.apps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.main.apps.AppsUseCase
import com.iwelogic.domain.models.Result
import com.iwelogic.presentation.models.AppPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(private val appsUseCase: AppsUseCase, private val mapper: AppDomainPresentationMapper) : BaseViewModel() {

    val openDetails: SingleLiveEvent<AppPresentation> = SingleLiveEvent()
    val apps: MutableLiveData<List<AppPresentation>> = MutableLiveData(ArrayList())

    init {
        onReload()
    }

    val onClick: (AppPresentation) -> Unit = {
        openDetails.postValue(it)
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
                    is Result.Success -> apps.postValue(result.data?.map { app -> mapper.map(app) })
                    is Result.Error -> error.postValue(result.message)
                }
            }
        }
    }
}
