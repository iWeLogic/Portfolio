package com.iwelogic.portfolio.presentation.sign_in.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.portfolio.presentation.R
import com.iwelogic.portfolio.presentation.databinding.FragmentRegisterBinding
import com.iwelogic.portfolio.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }
}