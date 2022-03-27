package com.iwelogic.portfolio.ui.news.news_details

import androidx.lifecycle.MutableLiveData
import com.iwelogic.portfolio.domain.models.News
import com.iwelogic.portfolio.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor() : BaseViewModel() {

    val news: MutableLiveData<News> = MutableLiveData()
}
