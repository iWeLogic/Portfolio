package com.iwelogic.presentation.ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.iwelogic.core.utils.hideKeyboard
import com.iwelogic.presentation.ui.MainActivity

open class BaseDialog<VM : BaseViewModel> : DialogFragment() {

    lateinit var viewModel: VM

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.close.observe(this) {
            dismiss()
        }

        viewModel.hideKeyboard.observe(viewLifecycleOwner) {
            (activity as MainActivity).hideKeyboard(true)
        }

        viewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}