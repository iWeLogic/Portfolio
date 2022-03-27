package com.iwelogic.portfolio.ui.sign_in.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.portfolio.R
import com.iwelogic.portfolio.databinding.FragmentForgotPasswordBinding
import com.iwelogic.portfolio.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<ForgotPasswordViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentForgotPasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }
}