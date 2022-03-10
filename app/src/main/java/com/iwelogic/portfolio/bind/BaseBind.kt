package com.iwelogic.portfolio.bind

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.iwelogic.portfolio.domain.models.News
import com.iwelogic.portfolio.ui.news.NewsAdapter

object BaseBind {

    @BindingAdapter("text")
    @JvmStatic
    fun setText(view: TextView, text: Any?) {
        text?.let {
            when (text) {
                is Int -> view.setText(text)
                is String -> view.text = text
                else -> view.text = text.toString()
            }
        } ?: run {
            view.text = ""
        }
    }

    @BindingAdapter("image")
    @JvmStatic
    fun setImage(view: ImageView, image: String?) {
        image?.let {
            Glide.with(view.context).load(image).transform(
                when (view.scaleType) {
                    ImageView.ScaleType.CENTER_CROP -> CenterCrop()
                    ImageView.ScaleType.CENTER_INSIDE -> CenterInside()
                    ImageView.ScaleType.FIT_CENTER -> FitCenter()
                    else -> CircleCrop()
                }
            ).into(view)
        }
    }
}