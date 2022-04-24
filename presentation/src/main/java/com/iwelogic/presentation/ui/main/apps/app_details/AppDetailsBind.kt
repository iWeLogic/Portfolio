package com.iwelogic.presentation.ui.main.apps.app_details

import androidx.databinding.BindingAdapter
import com.iwelogic.presentation.views.CollapseTextView
import com.smarteist.autoimageslider.SliderView

@BindingAdapter("images")
fun SliderView.setImages(images: List<String>?) {
    val sliderAdapter = ImageAdapter()
    sliderAdapter.renewItems(images ?: ArrayList())
    setSliderAdapter(sliderAdapter)
    startAutoCycle()
    setCurrentPageListener {
        let {
            it.stopAutoCycle()
            it.startAutoCycle()
        }
    }
}

@BindingAdapter("body")
fun CollapseTextView.setCollapseView(body: String?) {
    setBody(body)
    setDefaultExpandState(true)
}