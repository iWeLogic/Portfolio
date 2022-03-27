package com.iwelogic.portfolio.ui.main.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.portfolio.R
import com.iwelogic.portfolio.databinding.FragmentInfoBinding
import com.iwelogic.portfolio.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<InfoViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }
}
