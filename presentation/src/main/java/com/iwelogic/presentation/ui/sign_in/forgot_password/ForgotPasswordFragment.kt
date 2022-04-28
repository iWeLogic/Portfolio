package com.iwelogic.presentation.ui.sign_in.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.FragmentForgotPasswordBinding
import com.iwelogic.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<ForgotPasswordViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentForgotPasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }
}