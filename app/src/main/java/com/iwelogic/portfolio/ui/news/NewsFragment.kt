package com.iwelogic.portfolio.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.portfolio.R
import com.iwelogic.portfolio.databinding.FragmentNewsBinding
import com.iwelogic.portfolio.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<NewsViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentNewsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openDetails.observe(this) {
            findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment())
        }
    }
}