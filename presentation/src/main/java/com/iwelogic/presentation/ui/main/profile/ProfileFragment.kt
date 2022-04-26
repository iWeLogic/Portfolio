package com.iwelogic.presentation.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.FragmentProfileBinding
import com.iwelogic.presentation.ui.MainActivity
import com.iwelogic.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.user = (activity as MainActivity).viewModel.user
        viewModel.onReload()
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openLogin.observe(this) {
            if (findNavController().currentDestination?.id == R.id.profileFragment) {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
            }
        }
    }
}
