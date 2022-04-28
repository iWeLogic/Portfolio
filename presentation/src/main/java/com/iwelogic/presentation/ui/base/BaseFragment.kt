package com.iwelogic.presentation.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.iwelogic.core.utils.hideKeyboard
import com.iwelogic.presentation.R
import com.iwelogic.presentation.ui.MainActivity

open class BaseFragment<VM : BaseViewModel> : Fragment() {

    lateinit var viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.close.observe(this) {
            activity?.onBackPressed()
        }

        viewModel.hideKeyboard.observe(viewLifecycleOwner) {
            (activity as MainActivity).hideKeyboard(true)
        }

        viewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.showPopup.observe(this) { popup ->
            context?.let {
                val builder = AlertDialog.Builder(it, R.style.CustomAlertDialog).create()
                val dialogView = layoutInflater.inflate(R.layout.dialog_warning, null)
                dialogView.findViewById<TextView>(R.id.message).text = popup.text
                builder.setView(dialogView)
                val btnCancel = dialogView.findViewById<TextView>(R.id.btnCancel)
                val btnOk = dialogView.findViewById<TextView>(R.id.btnOk)
                btnCancel.visibility = if (popup.btnCancelTitle.isNullOrEmpty()) View.GONE else View.VISIBLE
                btnOk.visibility = if (popup.btnOkTitle.isNullOrEmpty()) View.GONE else View.VISIBLE
                btnCancel.text = popup.btnCancelTitle
                btnOk.text = popup.btnOkTitle
                btnCancel.setOnClickListener {
                    popup.btnCancelCallBack?.invoke()
                    builder.dismiss()
                }
                btnOk.setOnClickListener {
                    popup.btnOkCallBack?.invoke()
                    builder.dismiss()
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }
        }
    }
}