package com.iwelogic.presentation.ui.main.feedbacks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.FragmentFeedbacksBinding
import com.iwelogic.presentation.ui.MainActivity
import com.iwelogic.presentation.ui.base.BaseFragment
import com.iwelogic.presentation.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbacksFragment : BaseFragment<FeedbacksViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentFeedbacksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedbacks, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[FeedbacksViewModel::class.java]
        viewModel.user = (activity as MainActivity).viewModel.user
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openLogin.observe(this) {
            if (parentFragment?.parentFragment?.findNavController()?.currentDestination?.id == R.id.mainFragment) {
                parentFragment?.parentFragment?.findNavController()?.navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
            }
        }
    }
}
