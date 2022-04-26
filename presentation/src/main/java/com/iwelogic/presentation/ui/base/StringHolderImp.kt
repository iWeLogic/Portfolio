package com.iwelogic.presentation.ui.base

import android.content.Context
import java.lang.ref.WeakReference

class StringHolderImp(context: Context) : StringHolder {

    var context: WeakReference<Context> = WeakReference(context)

    override fun getString(id: Int): String = context.get()?.getString(id) ?: ""
}