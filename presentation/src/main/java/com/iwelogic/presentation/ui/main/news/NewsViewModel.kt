package com.iwelogic.presentation.ui.main.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.core.utils.isTrue
import com.iwelogic.core.utils.value
import com.iwelogic.domain.main.news.NewsUseCase
import com.iwelogic.domain.models.Result
import com.iwelogic.presentation.models.CellType
import com.iwelogic.presentation.models.NewsPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import com.iwelogic.presentation.ui.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private var newsUseCase: NewsUseCase,
    private val mapper: NewsDomainPresentationMapper
) : BaseViewModel() {

    companion object {
        const val PAGE_SIZE = 6
    }

    val openDetails: SingleLiveEvent<NewsPresentation> = SingleLiveEvent()
    val news: MutableLiveData<MutableList<NewsPresentation>> = MutableLiveData(ArrayList())
    private var canLoadMore = true
    private var requestNewsLoading: Job? = null

    init {
        load()
    }

    var onScroll: (Int) -> Unit = {
        if (it > news.value?.size.value() - 2) {
            load()
        }
    }

    private fun load() {
        if (requestNewsLoading?.isActive.isTrue() || !canLoadMore) return
        error.postValue(null)
        requestNewsLoading = viewModelScope.launch {
            newsUseCase.getNews(PAGE_SIZE, getOffset()).catch {
                error.postValue(it.message)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> showProgress(true)
                    is Result.Finish -> showProgress(false)
                    is Result.Success -> {
                        result.data?.map { news -> mapper.map(news) }?.let {
                            news.value?.addAll(it)
                            news.postValue(news.value)
                            if (it.size < PAGE_SIZE) canLoadMore = false
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

    val onClickItem: (NewsPresentation) -> Unit = {
        openDetails.postValue(it)
    }

    override fun onReload() {
        requestNewsLoading?.cancel()
        canLoadMore = true
        news.value?.clear()
        news.postValue(news.value)
        load()
    }

    private fun getOffset() = news.value?.filter { it.type == CellType.SIMPLE }?.size ?: 0

    private fun showProgress(status: Boolean) {
        if (status) {
            if (news.value.isNullOrEmpty()) {
                progress.postValue(true)
            } else {
                news.value?.add(NewsPresentation.getProgressItem())
                news.postValue(news.value)
            }
        } else {
            progress.postValue(false)
            news.value?.remove(NewsPresentation.getProgressItem())
            news.postValue(news.value)
        }
    }
}
