package com.iwelogic.portfolio.ui.news.news_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.portfolio.R
import com.iwelogic.portfolio.databinding.FragmentNewsDetailsBinding
import com.iwelogic.portfolio.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : BaseFragment<NewsDetailsViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentNewsDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[NewsDetailsViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }
}
