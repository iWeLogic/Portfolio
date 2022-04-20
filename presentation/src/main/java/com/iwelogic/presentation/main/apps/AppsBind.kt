package com.iwelogic.presentation.main.apps

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.presentation.models.App

object AppsBind {

    @BindingAdapter("apps", "onClick")
    @JvmStatic
    fun showApps(view: RecyclerView, apps: List<App>?, onClick: (App) -> Unit) {
        view.adapter ?: run {
            view.adapter = AppAdapter(onClick)
        }
        (view.adapter as AppAdapter).submitList(apps?.toMutableList())
    }
}