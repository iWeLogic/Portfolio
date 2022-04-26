package com.iwelogic.presentation.ui.main.apps

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.presentation.models.PresentationApp

@BindingAdapter("apps", "onClick")
fun RecyclerView.showApps(apps: List<PresentationApp>?, onClick: (PresentationApp) -> Unit) {
    adapter ?: run {
        adapter = AppAdapter(onClick)
    }
    (adapter as AppAdapter).submitList(apps?.toMutableList())
}