package com.iwelogic.presentation.ui.main.news

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.presentation.models.NewsPresentation

@BindingAdapter("news", "onClick", "onScroll")
fun RecyclerView.showNews(news: List<NewsPresentation>?, onClick: (NewsPresentation) -> Unit, onScroll: (Int) -> Unit) {
    adapter ?: run {
        adapter = NewsAdapter(onClick)
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                onScroll.invoke((recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition())
            }
        })
    }
    (adapter as NewsAdapter).submitList(news?.toMutableList())
}