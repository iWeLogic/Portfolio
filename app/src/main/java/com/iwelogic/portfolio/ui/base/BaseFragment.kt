package com.iwelogic.portfolio.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.iwelogic.portfolio.R

open class BaseFragment<VM : BaseViewModel> : Fragment() {

    lateinit var viewModel: VM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.close.observe(this) {
            activity?.onBackPressed()
        }

        viewModel.warning.observe(this) { warning ->
            context?.let {
                val builder = AlertDialog.Builder(it, R.style.CustomAlertDialog).create()
                val dialogView = layoutInflater.inflate(R.layout.dialog_warning, null)
                dialogView.findViewById<TextView>(R.id.message).text = warning
                builder.setView(dialogView)
                dialogView.findViewById<View>(R.id.btnOk).setOnClickListener {
                    builder.dismiss()
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }
        }
    }

    /* override fun showToast(msg: String?) {
         context?.let {
             Toast.makeText(it, msg, Toast.LENGTH_LONG).show()
         }
     }

     override fun showToast(msg: Int) {
         context?.let {
             Toast.makeText(it, msg, Toast.LENGTH_LONG).show()
         }
     }


     override fun openLogin() {

     }

     override fun openMain(isFirstLaunch: Boolean) {

     }*/

}