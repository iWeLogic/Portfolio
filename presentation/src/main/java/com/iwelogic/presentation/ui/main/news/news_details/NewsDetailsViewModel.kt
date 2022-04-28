package com.iwelogic.presentation.ui.main.news.news_details

import androidx.lifecycle.MutableLiveData
import com.iwelogic.presentation.models.NewsPresentation
import com.iwelogic.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor() : BaseViewModel() {

    val newsPresentation: MutableLiveData<NewsPresentation> = MutableLiveData()
}
