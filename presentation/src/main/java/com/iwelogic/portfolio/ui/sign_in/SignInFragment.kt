package com.iwelogic.portfolio.ui.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.portfolio.R
import com.iwelogic.portfolio.databinding.FragmentSignInBinding
import com.iwelogic.portfolio.ui.MainActivity
import com.iwelogic.portfolio.ui.base.BaseFragment
import com.iwelogic.portfolio.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentSignInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        binding.viewModel = viewModel
        subscribe()
        return binding.root
    }

    private fun subscribe() {
        viewModel.openMain.observe(this) {
            (activity as MainActivity).openMain()
        }
        viewModel.openRegister.observe(this) {
            if (findNavController().currentDestination?.id == R.id.signInFragment) {
                findNavController().navigate(MainFragmentDirections.actionGlobalRegisterFragment())
            }
        }
        viewModel.openForgotPassword.observe(this) {
            if (findNavController().currentDestination?.id == R.id.signInFragment) {
                findNavController().navigate(MainFragmentDirections.actionGlobalForgotPasswordFragment(it))
            }
        }
    }
}
