package com.iwelogic.presentation.ui.sign_in.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.presentation.R
import com.iwelogic.presentation.ui.base.BaseFragment
import com.iwelogic.presentation.ui.base.Const.VALUE
import com.iwelogic.presentation.databinding.FragmentRegisterBinding
import com.iwelogic.presentation.ui.sign_in.login.LoginFragment.Companion.REGISTER_RESULT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.returnRegisteredUser.observe(this) {
            setFragmentResult(REGISTER_RESULT, bundleOf(VALUE to it))
        }
    }
}