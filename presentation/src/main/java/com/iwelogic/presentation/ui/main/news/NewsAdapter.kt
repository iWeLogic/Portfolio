package com.iwelogic.presentation.ui.main.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.presentation.models.CellType
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.ItemNewsBinding
import com.iwelogic.presentation.models.NewsPresentation

class NewsAdapter(private val onClick: (NewsPresentation) -> Unit) : ListAdapter<NewsPresentation, RecyclerView.ViewHolder>(ComparatorNews) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CellType.PROGRESS.id -> ProgressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
            else -> NewsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_news, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            holder.bind(getItem(position))
        }
    }

    internal inner class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsPresentation) {
            binding.item = item
            binding.root.setOnClickListener {
                onClick.invoke(item)
            }
            binding.executePendingBindings()
        }
    }

    internal inner class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = getItem(position).type.id
}

object ComparatorNews : DiffUtil.ItemCallback<NewsPresentation>() {
    override fun areItemsTheSame(oldItem: NewsPresentation, newItem: NewsPresentation): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: NewsPresentation, newItem: NewsPresentation): Boolean = oldItem == newItem
}
