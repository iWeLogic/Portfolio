package com.iwelogic.presentation.ui.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.FragmentNewsBinding
import com.iwelogic.presentation.ui.base.BaseFragment
import com.iwelogic.presentation.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<NewsViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentNewsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openDetails.observe(this) {
            if (parentFragment?.parentFragment?.findNavController()?.currentDestination?.id == R.id.mainFragment)
                parentFragment?.parentFragment?.findNavController()?.navigate(MainFragmentDirections.actionMainFragmentToNewsDetailsFragment(it))
        }
    }
}
