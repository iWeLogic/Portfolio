package com.iwelogic.ui.base

import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment

open class BaseDialog<T : BaseNavigator, VM : BaseViewModel<T>> : DialogFragment(), BaseNavigator {

    lateinit var viewModel: VM

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(null)
    }

    override fun showToast(msg: String?) {
        context?.let {
            Toast.makeText(it, msg, Toast.LENGTH_LONG).show()
        }
    }

    override fun showToast(msg: Int) {
        context?.let {
            Toast.makeText(it, msg, Toast.LENGTH_LONG).show()
        }
    }

    override fun close() {
        dismiss()
    }

    override fun openLogin() {

    }

    override fun openMain(isFirstLaunch: Boolean) {

    }

    override fun showPopupWarning(message: String) {

    }

    override fun openOnboarding() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.navigator = null
    }
}