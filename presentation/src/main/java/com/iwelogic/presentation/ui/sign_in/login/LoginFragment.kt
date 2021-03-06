package com.iwelogic.presentation.ui.sign_in.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.presentation.ui.MainActivity
import com.iwelogic.presentation.R
import com.iwelogic.presentation.ui.base.BaseFragment
import com.iwelogic.presentation.ui.base.Const.VALUE
import com.iwelogic.presentation.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>() {

    companion object {
        const val REGISTER_RESULT = "REGISTER_RESULT"
        const val FORGOT_RESULT = "FORGOT_RESULT"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.user = (activity as MainActivity).viewModel.user
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        setFragmentResultListener(FORGOT_RESULT) { _, bundle ->
            viewModel.email.postValue(bundle.getString(VALUE))
        }
    }
}
