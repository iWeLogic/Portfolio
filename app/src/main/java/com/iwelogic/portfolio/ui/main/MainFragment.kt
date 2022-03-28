package com.iwelogic.portfolio.ui.main

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
        val navController = (childFragmentManager.findFragmentById(R.id.bottomNavigationContainer) as NavHostFragment).navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.title.postValue(
                when (destination.id) {
                    R.id.appsFragment -> view.context.getString(R.string.apps)
                    R.id.newsFragment -> view.context.getString(R.string.news)
                    R.id.feedbacksFragment -> view.context.getString(R.string.feedbacks)
                    else -> view.context.getString(R.string.info)
                }
            )
        }
        NavigationUI.setupWithNavController(view.findViewById<BottomNavigationView>(R.id.bottomNavigationView), navController)
    }
}
