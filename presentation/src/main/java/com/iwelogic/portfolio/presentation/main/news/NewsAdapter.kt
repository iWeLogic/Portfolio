package com.iwelogic.portfolio.presentation.main.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.portfolio.presentation.R
import com.iwelogic.portfolio.presentation.databinding.ItemNewsBinding
import com.iwelogic.portfolio.domain.models.CellType
import com.iwelogic.portfolio.domain.models.News

class NewsAdapter(private val onClick: (News) -> Unit) : ListAdapter<News, RecyclerView.ViewHolder>(ComparatorNews) {

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
        fun bind(item: News) {
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

object ComparatorNews : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
}
