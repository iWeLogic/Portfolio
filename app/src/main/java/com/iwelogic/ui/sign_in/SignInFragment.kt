package com.iwelogic.ui.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.R
import com.iwelogic.databinding.FragmentSignInBinding
import com.iwelogic.ui.base.BaseFragment
import com.iwelogic.ui.main.MainFragmentDirections
import com.iwelogic.utils.catchAll
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
        viewModel.openRegister.observe(this) {
            catchAll {
                findNavController().navigate(MainFragmentDirections.actionGlobalRegisterFragment())
            }
        }
        viewModel.openForgotPassword.observe(this) {
            catchAll {
                findNavController().navigate(MainFragmentDirections.actionGlobalForgotPasswordFragment(it))
            }
        }
    }
}
