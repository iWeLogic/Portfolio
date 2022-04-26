package com.iwelogic.presentation.ui.main.news.news_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.FragmentNewsDetailsBinding
import com.iwelogic.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : BaseFragment<NewsDetailsViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentNewsDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[NewsDetailsViewModel::class.java]
//        viewModel.news.postValue(NewsDetailsFragmentArgs.fromBundle(requireArguments()).data)
        binding.viewModel = viewModel
        return binding.root
    }
}
