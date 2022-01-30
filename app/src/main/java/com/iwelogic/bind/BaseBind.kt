package com.iwelogic.bind

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BaseBind {

    @BindingAdapter("text")
    @JvmStatic
    fun setText(view: TextView, text: Any?) {
        text?.let {
            when (text) {
                is Int -> view.setText(text)
                is String -> view.text = text
                else -> view.text = text.toString()
            }
        } ?: run {
            view.text = ""
        }
    }

}