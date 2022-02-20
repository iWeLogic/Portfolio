package com.iwelogic.ui.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.R
import com.iwelogic.data.repository.RepositoryImp
import com.iwelogic.data.source.DataSourceImp
import com.iwelogic.databinding.FragmentForgotPasswordBinding
import com.iwelogic.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<ForgotPasswordViewModel>() {

    @Inject
    lateinit var viewModelFactory: ForgotPasswordViewModelFactory

    @Inject
    lateinit var dataSource: DataSourceImp

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentForgotPasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, ForgotPasswordViewModel.provideFactory(viewModelFactory, RepositoryImp(dataSource))).get(ForgotPasswordViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }
}