package com.iwelogic.portfolio.ui.main.apps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.portfolio.R
import com.iwelogic.portfolio.databinding.ItemAppBinding
import com.iwelogic.portfolio.domain.models.App

class AppAdapter(private val onClick: (App) -> Unit) : ListAdapter<App, RecyclerView.ViewHolder>(ComparatorApp) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_app, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AppViewHolder) {
            holder.bind(getItem(position))
        }
    }

    internal inner class AppViewHolder(private val binding: ItemAppBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: App) {
            binding.item = item
            binding.root.setOnClickListener {
                onClick.invoke(item)
            }
            binding.executePendingBindings()
        }
    }
}

object ComparatorApp : DiffUtil.ItemCallback<App>() {
    override fun areItemsTheSame(oldItem: App, newItem: App): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: App, newItem: App): Boolean = oldItem == newItem
}
