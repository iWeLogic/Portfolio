package com.iwelogic.portfolio.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.portfolio.R
import com.iwelogic.portfolio.databinding.FragmentLoginBinding
import com.iwelogic.portfolio.ui.base.BaseFragment
import com.iwelogic.portfolio.ui.main_activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = viewModel
        subscribe()
        return binding.root
    }

    private fun subscribe() {
        viewModel.openMain.observe(this) {
            (activity as MainActivity).openMain()
        }
        viewModel.openRegister.observe(this) {
            if (findNavController().currentDestination?.id == R.id.loginFragment) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
        viewModel.openForgotPassword.observe(this) {
            if (findNavController().currentDestination?.id == R.id.loginFragment) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment(it))
            }
        }
    }
}
