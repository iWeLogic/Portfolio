package com.iwelogic.portfolio.presentation.main.apps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.portfolio.presentation.R
import com.iwelogic.portfolio.presentation.databinding.FragmentAppsBinding
import com.iwelogic.portfolio.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppsFragment : BaseFragment<AppsViewModel>(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentAppsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_apps, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[AppsViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }
}
