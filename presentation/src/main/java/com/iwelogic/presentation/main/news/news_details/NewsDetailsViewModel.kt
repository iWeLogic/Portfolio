package com.iwelogic.presentation.main.news.news_details

import androidx.lifecycle.MutableLiveData
import com.iwelogic.domain.models.DomainNews
import com.iwelogic.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor() : BaseViewModel() {

    val news: MutableLiveData<DomainNews> = MutableLiveData()
}
