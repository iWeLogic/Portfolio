package com.iwelogic.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.iwelogic.R
import com.iwelogic.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.checkIsLogged()
        subscribe()
    }

    private fun subscribe() {
        viewModel.openMain.observe(this) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostContainer) as NavHostFragment
            val graphInflater = navHostFragment.navController.navInflater
            val navGraph = graphInflater.inflate(R.navigation.main)
            navGraph.startDestination = R.id.mainFragment
            navHostFragment.navController.graph = navGraph
        }

        viewModel.openLogin.observe(this) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostContainer) as NavHostFragment
            val graphInflater = navHostFragment.navController.navInflater
            val navGraph = graphInflater.inflate(R.navigation.main)
            navGraph.startDestination = R.id.signInFragment
            navHostFragment.navController.graph = navGraph
        }
    }
}