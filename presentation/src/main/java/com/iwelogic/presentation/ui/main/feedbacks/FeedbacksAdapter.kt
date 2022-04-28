package com.iwelogic.presentation.ui.main.feedbacks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.ItemFeedbacksBinding
import com.iwelogic.presentation.models.CellType
import com.iwelogic.presentation.models.FeedbackPresentation

class FeedbacksAdapter : ListAdapter<FeedbackPresentation, RecyclerView.ViewHolder>(ComparatorNews) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CellType.PROGRESS.id -> ProgressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
            else -> FeedbacksViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_feedbacks, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FeedbacksViewHolder) {
            holder.bind(getItem(position))
        }
    }

    internal inner class FeedbacksViewHolder(private val binding: ItemFeedbacksBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedbackPresentation) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    internal inner class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = getItem(position).type.id
}

object ComparatorNews : DiffUtil.ItemCallback<FeedbackPresentation>() {
    override fun areItemsTheSame(oldItem: FeedbackPresentation, newItem: FeedbackPresentation): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: FeedbackPresentation, newItem: FeedbackPresentation): Boolean = oldItem == newItem
}
