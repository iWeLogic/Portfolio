package com.iwelogic.presentation.ui.main.feedbacks

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iwelogic.core.utils.isTrue
import com.iwelogic.presentation.R
import com.iwelogic.presentation.models.FeedbackPresentation

@BindingAdapter("feedbacks", "onScroll")
fun RecyclerView.showFeedbacks(feedbacks: List<FeedbackPresentation>?, onScroll: (Int) -> Unit) {
    val fab: FloatingActionButton? = (parent as CoordinatorLayout).findViewById(R.id.fab)
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0 && fab?.isShown.isTrue()) {
                fab?.hide()
            } else if (dy < 0 && !fab?.isShown.isTrue()) {
                fab?.show()
            }
        }
    })
    adapter ?: run {
        adapter = FeedbacksAdapter()
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                onScroll.invoke((recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition())
            }
        })
        adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) scrollToPosition(0)
            }
        })
    }
    (adapter as FeedbacksAdapter).submitList(feedbacks?.toMutableList())
}