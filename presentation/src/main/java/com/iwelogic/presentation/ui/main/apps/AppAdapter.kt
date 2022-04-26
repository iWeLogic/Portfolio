package com.iwelogic.presentation.ui.main.apps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.presentation.models.AppPresentation
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.ItemAppBinding

class AppAdapter(private val onClick: (AppPresentation) -> Unit) : ListAdapter<AppPresentation, RecyclerView.ViewHolder>(ComparatorApp) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_app, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AppViewHolder) {
            holder.bind(getItem(position))
        }
    }

    private inner class AppViewHolder(private val binding: ItemAppBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AppPresentation) {
            binding.item = item
            binding.root.setOnClickListener {
                onClick.invoke(item)
            }
            binding.executePendingBindings()
        }
    }
}

object ComparatorApp : DiffUtil.ItemCallback<AppPresentation>() {
    override fun areItemsTheSame(oldItem: AppPresentation, newItem: AppPresentation): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: AppPresentation, newItem: AppPresentation): Boolean = oldItem == newItem
}
