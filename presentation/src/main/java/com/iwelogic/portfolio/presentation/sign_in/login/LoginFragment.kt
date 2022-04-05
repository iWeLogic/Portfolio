package com.iwelogic.portfolio.presentation.sign_in.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.portfolio.presentation.MainActivity
import com.iwelogic.portfolio.presentation.R
import com.iwelogic.portfolio.presentation.base.BaseFragment
import com.iwelogic.portfolio.presentation.base.Const.VALUE
import com.iwelogic.portfolio.presentation.databinding.FragmentLoginBinding
import com.iwelogic.portfolio.presentation.models.SignIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>() {

    companion object {
        const val REGISTER_RESULT = "REGISTER_RESULT"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        setFragmentResultListener(REGISTER_RESULT) { _, bundle ->
            viewModel.loginWithRegisteredUser(bundle.getParcelable(VALUE))
        }
    }
}
