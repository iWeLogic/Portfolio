package com.iwelogic.presentation.ui.main.apps

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.presentation.models.AppPresentation

@BindingAdapter("apps", "onClick")
fun RecyclerView.showApps(appPresentations: List<AppPresentation>?, onClick: (AppPresentation) -> Unit) {
    adapter ?: run {
        adapter = AppAdapter(onClick)
    }
    (adapter as AppAdapter).submitList(appPresentations?.toMutableList())
}