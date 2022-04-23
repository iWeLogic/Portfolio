package com.iwelogic.presentation.ui.main.apps

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.presentation.models.PresentationApp

object AppsBind {

    @BindingAdapter("apps", "onClick")
    @JvmStatic
    fun showApps(view: RecyclerView, apps: List<PresentationApp>?, onClick: (PresentationApp) -> Unit) {
        view.adapter ?: run {
            view.adapter = AppAdapter(onClick)
        }
        (view.adapter as AppAdapter).submitList(apps?.toMutableList())
    }
}