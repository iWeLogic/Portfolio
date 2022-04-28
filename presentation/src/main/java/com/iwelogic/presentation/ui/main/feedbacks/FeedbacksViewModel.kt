package com.iwelogic.presentation.ui.main.feedbacks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.core.utils.isTrue
import com.iwelogic.core.utils.value
import com.iwelogic.domain.main.feedbacks.FeedbacksUseCase
import com.iwelogic.domain.models.Result
import com.iwelogic.presentation.models.CellType
import com.iwelogic.presentation.models.FeedbackPresentation
import com.iwelogic.presentation.models.UserPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbacksViewModel @Inject constructor(
    private val feedbacksUseCase: FeedbacksUseCase,
    private val mapper: FeedbackDomainPresentationMapper
) : BaseViewModel() {

    companion object {
        const val PAGE_SIZE = 10
    }

    var user: MutableLiveData<UserPresentation> = MutableLiveData()
    val feedback: MutableLiveData<MutableList<FeedbackPresentation>> = MutableLiveData(ArrayList())
    val openLogin: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val openAddFeedback: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private var canLoadMore = true
    private var requestLoading: Job? = null

    init {
        load()
    }

    fun onClickLogin() {
        openLogin.postValue(true)
    }

    var onScroll: (Int) -> Unit = {
        if (it > feedback.value?.size.value() - 2) {
            load()
        }
    }

    private fun load() {
        if (requestLoading?.isActive.isTrue() || !canLoadMore) return
        error.postValue(null)
        requestLoading = viewModelScope.launch {
            feedbacksUseCase.getFeedbacks(PAGE_SIZE, getOffset()).catch {
                error.postValue(it.message)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> showProgress(true)
                    is Result.Finish -> showProgress(false)
                    is Result.Success -> result.data?.map { feedback -> mapper.map(feedback) }?.let {
                        feedback.value?.addAll(it)
                        feedback.postValue(feedback.value)
                        if (it.size < PAGE_SIZE) canLoadMore = false
                    }
                    is Result.Error -> {
                        canLoadMore = false
                        error.postValue(result.message)
                    }
                }
            }
        }
    }

    fun onClickAddFeedback() {
        openAddFeedback.postValue(true)
    }

    fun addFeedback(data: FeedbackPresentation) {
        data.name = "${user.value?.firstName} ${user.value?.lastName} "
        viewModelScope.launch {
            feedbacksUseCase.addFeedback(mapper.reverseMap(data)).catch {
                error.postValue(it.message)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> progress.postValue(true)
                    is Result.Finish -> progress.postValue(false)
                    is Result.Success -> result.data?.let { feedbackDomain ->
                        mapper.map(feedbackDomain).let { feedbackPresentation ->
                            feedback.value?.add(0, feedbackPresentation)
                            feedback.postValue(feedback.value)
                        }
                    }
                    is Result.Error -> {
                        canLoadMore = false
                        error.postValue(result.message)
                    }
                }
            }
        }
    }


    override fun onReload() {
        user.value?.let {
            requestLoading?.cancel()
            canLoadMore = true
            feedback.value?.clear()
            feedback.postValue(feedback.value)
            load()
        }
    }

    private fun getOffset() = feedback.value?.filter { it.type == CellType.SIMPLE }?.size ?: 0

    private fun showProgress(status: Boolean) {
        if (status) {
            if (feedback.value.isNullOrEmpty()) {
                progress.postValue(true)
            } else {
                feedback.value?.add(FeedbackPresentation.getProgressItem())
                feedback.postValue(feedback.value)
            }
        } else {
            progress.postValue(false)
            feedback.value?.remove(FeedbackPresentation.getProgressItem())
            feedback.postValue(feedback.value)
        }
    }
}
