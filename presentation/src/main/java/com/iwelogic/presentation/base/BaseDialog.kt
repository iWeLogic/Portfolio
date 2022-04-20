package com.iwelogic.presentation.base

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

open class BaseDialog<VM : BaseViewModel> : DialogFragment() {

    lateinit var viewModel: VM

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(null)
    }
}