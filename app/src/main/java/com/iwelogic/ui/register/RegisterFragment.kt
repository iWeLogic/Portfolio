package com.iwelogic.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.R
import com.iwelogic.data.repository.RepositoryImp
import com.iwelogic.data.source.DataSourceImp
import com.iwelogic.databinding.FragmentRegisterBinding
import com.iwelogic.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterNavigator, RegisterViewModel>(), RegisterNavigator {

    @Inject
    lateinit var dataSource: DataSourceImp

    @Inject
    lateinit var viewModelFactory: RegisterViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, RegisterViewModel.provideFactory(viewModelFactory, RepositoryImp(dataSource)))[RegisterViewModel::class.java]
        viewModel.navigator = this
        binding.viewModel = viewModel
        return binding.root
    }
}