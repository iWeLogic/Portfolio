package com.iwelogic.presentation.ui.main.apps.app_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.presentation.R
import com.iwelogic.presentation.ui.base.BaseFragment
import com.iwelogic.presentation.databinding.FragmentAppDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppDetailsFragment : BaseFragment<AppDetailsViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentAppDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_app_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[AppDetailsViewModel::class.java]
        viewModel.app.postValue(AppDetailsFragmentArgs.fromBundle(requireArguments()).data)
        binding.viewModel = viewModel
        return binding.root
    }
}
