package com.iwelogic.presentation.ui.main.apps.app_details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.iwelogic.presentation.R
import com.iwelogic.presentation.databinding.ItemImageBinding
import com.smarteist.autoimageslider.SliderViewAdapter
import java.util.*

class ImageAdapter : SliderViewAdapter<ImageAdapter.ImageViewHolder>() {

    private var images: List<String> = ArrayList()

    fun renewItems(images: List<String>) {
        this.images = images
        notifyDataSetChanged()
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup): ImageViewHolder {
        return ImageViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_image, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ImageViewHolder, position: Int) {
        viewHolder.bind(images[position])
    }

    override fun getCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) : SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}