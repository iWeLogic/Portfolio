package com.iwelogic.portfolio.ui.news

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iwelogic.portfolio.domain.models.News

object NewsBind {

    @BindingAdapter("news", "onClick", "onScroll")
    @JvmStatic
    fun showNews(view: RecyclerView, news: List<News>?, onClick: (News) -> Unit, onScroll: (Int) -> Unit) {
        view.adapter ?: run {
            view.adapter = NewsAdapter(onClick)
            view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    onScroll.invoke((recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition())
                }
            })
        }
        (view.adapter as NewsAdapter).submitList(news?.toMutableList())
    }
}