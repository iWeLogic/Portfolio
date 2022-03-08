package com.iwelogic.portfolio.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment<VM : BaseViewModel> : Fragment() {

    lateinit var viewModel: VM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.close.observe(this) {
            activity?.onBackPressed()
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

/*    override fun showWarningDialog(message: String) {
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
    }*/
}