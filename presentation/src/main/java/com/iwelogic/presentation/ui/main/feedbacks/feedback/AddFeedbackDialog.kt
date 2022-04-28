package com.iwelogic.presentation.ui.main.feedbacks.feedback

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.DialogAddFeedbackBinding
import com.iwelogic.presentation.ui.base.BaseDialog
import com.iwelogic.presentation.ui.base.Const
import com.iwelogic.presentation.ui.main.feedbacks.FeedbacksFragment.Companion.ADD_FEEDBACK_RESULT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFeedbackDialog : BaseDialog<AddFeedbackViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: DialogAddFeedbackBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_feedback, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[AddFeedbackViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addFeedback.observe(this) {
            Log.w("myLog", "onViewCreated: iiii")
            setFragmentResult(ADD_FEEDBACK_RESULT, Bundle().apply { putParcelable(Const.VALUE, it) })
        }
    }
}
