package com.iwelogic.ui.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.R
import com.iwelogic.data.repository.RepositoryImpl
import com.iwelogic.data.source.DataSourceImpl
import com.iwelogic.databinding.FragmentSignInBinding
import com.iwelogic.ui.base.BaseFragment
import com.iwelogic.ui.main.MainFragmentDirections
import com.iwelogic.utils.catchAll
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInNavigator, SignInViewModel>(), SignInNavigator {

    @Inject
    lateinit var dataSource: DataSourceImpl

    @Inject
    lateinit var viewModelFactory: SignInViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentSignInBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, SignInViewModel.provideFactory(viewModelFactory, RepositoryImpl(dataSource))).get(SignInViewModel::class.java)
        viewModel.navigator = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun openRegister() {
        catchAll {
            findNavController().navigate(MainFragmentDirections.actionGlobalRegisterFragment())
        }
    }
}
