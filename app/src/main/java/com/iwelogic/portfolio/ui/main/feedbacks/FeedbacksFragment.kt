package com.iwelogic.portfolio.ui.main.feedbacks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.portfolio.R
import com.iwelogic.portfolio.databinding.FragmentFeedbacksBinding
import com.iwelogic.portfolio.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbacksFragment : BaseFragment<FeedbacksViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentFeedbacksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedbacks, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[FeedbacksViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }
}
