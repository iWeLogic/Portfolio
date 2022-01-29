package com.iwelogic.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment<T : BaseNavigator, VM : BaseViewModel<T>> : Fragment(), BaseNavigator {

    lateinit var viewModel: VM

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
        activity?.onBackPressed()
    }

    override fun openLogin() {
    }

    override fun openMain(isFirstLaunch: Boolean) {
    }

    override fun openOnboarding() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.navigator = null
    }

    override fun showPopupWarning(message: String) {

    }
}