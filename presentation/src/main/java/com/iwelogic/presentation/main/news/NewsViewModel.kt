package com.iwelogic.presentation.main.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iwelogic.core.utils.isTrue
import com.iwelogic.core.utils.value
import com.iwelogic.presentation.models.CellType
import com.iwelogic.presentation.models.News
import com.iwelogic.domain.main.news.NewsUseCase
import com.iwelogic.domain.models.Result
import com.iwelogic.presentation.base.BaseViewModel
import com.iwelogic.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(private var newsUseCase: NewsUseCase) : BaseViewModel() {

    companion object {
        const val PAGE_SIZE = 8
    }

    val openDetails: SingleLiveEvent<News> = SingleLiveEvent()
    val news: MutableLiveData<MutableList<News>> = MutableLiveData(ArrayList())
    var canLoadMore = true

    var requestNewsLoading: Job? = null

    init {
        load()
    }

    var onScroll: (Int) -> Unit = {
        if (it > news.value?.size.value() - 3) {
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
                Log.w("myLog", "load: collect")
                when (result) {
                    is Result.Loading -> showProgress(true)
                    is Result.Finish -> showProgress(false)
                    is Result.Success -> {
                        result.data?.map {
                            News(type = CellType.SIMPLE, id = it.id, title = it.title, description = it.description, image = it.image)
                        }?.let {
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

    val onClickItem: (News) -> Unit = {
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
        Log.w("myLog", "showProgress: " + status)
         if (status) {
             if (news.value.isNullOrEmpty()) {
                 progress.postValue(true)
             } else {
                 news.value?.add(News.getProgressItem())
                 news.postValue(news.value)
             }
         } else {
             progress.postValue(false)
             news.value?.remove(News.getProgressItem())
             news.postValue(news.value)
         }
    }
}
