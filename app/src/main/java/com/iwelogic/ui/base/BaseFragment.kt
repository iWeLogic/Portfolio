package com.iwelogic.ui.base

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iwelogic.R

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

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.navigator = null
    }

    override fun showWarningDialog(message: String) {
        context?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomAlertDialog).create()
            val view = layoutInflater.inflate(R.layout.dialog_warning, null)
            view.findViewById<TextView>(R.id.message).text = message
            builder.setView(view)
            view.findViewById<View>(R.id.btnOk).setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
    }
}