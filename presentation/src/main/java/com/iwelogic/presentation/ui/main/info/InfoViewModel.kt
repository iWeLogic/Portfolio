package com.iwelogic.presentation.ui.main.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.domain.main.info.InfoUseCase
import com.iwelogic.domain.models.Result
import com.iwelogic.presentation.models.InfoPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val infoUseCase: InfoUseCase, private val mapper: InfoDomainPresentationMapper) : BaseViewModel() {

    val info: MutableLiveData<InfoPresentation> = MutableLiveData()
    val dial: SingleLiveEvent<String> = SingleLiveEvent()
    val openEmail: SingleLiveEvent<String> = SingleLiveEvent()
    val openTelegram: SingleLiveEvent<String> = SingleLiveEvent()
    val openSkype: SingleLiveEvent<String> = SingleLiveEvent()
    val openUrl: SingleLiveEvent<String> = SingleLiveEvent()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            infoUseCase.getInfo().catch {
                error.postValue(it.message)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> info.postValue(result.data?.let { mapper.map(it) })
                    is Result.Error -> error.postValue(result.message)
                }
            }
        }
    }

    fun onClickPhone() {
        dial.postValue(info.value?.phone)
    }

    fun onClickEmail() {
        openEmail.postValue(info.value?.email)
    }

    fun onClickTelegram() {
        openTelegram.postValue(info.value?.telegram)
    }

    fun onClickSkype() {
        openSkype.postValue(info.value?.skype)
    }

    fun onClickLinkedin() {
        openUrl.postValue(info.value?.linkedin)
    }

    fun onClickUpWork() {
        openUrl.postValue(info.value?.upWork)
    }

    fun onClickResume() {
        openUrl.postValue(info.value?.resume)
    }
}
