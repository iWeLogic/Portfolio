package com.iwelogic.portfolio.ui.main_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iwelogic.portfolio.R
import com.iwelogic.portfolio.databinding.FragmentMainBinding
import com.iwelogic.portfolio.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openProfile.observe(this) {
            if (findNavController().currentDestination?.id == R.id.mainFragment) {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToProfileFragment())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val navHostFragment = childFragmentManager.findFragmentById(R.id.bottomNavigationContainer)
        NavigationUI.setupWithNavController(view!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView), (navHostFragment as NavHostFragment).navController)
    }
}
