package com.iwelogic.presentation.ui.main.apps.app_details

import android.util.TypedValue
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.iwelogic.core.utils.dp
import com.iwelogic.presentation.R
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

@BindingAdapter("tags")
fun FlexboxLayout.setTags(tags: List<String>?) {
    removeAllViews()
    tags?.forEach { tag ->
        val tagView = TextView(context)
        tagView.setTextColor(ContextCompat.getColor(context, R.color.regularTextColor))
        tagView.typeface = ResourcesCompat.getFont(context, R.font.open_sans_regular)
        tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        tagView.setBackgroundResource(R.drawable.background_tag)
        tagView.setPadding(12.dp(context), 4.dp(context), 12.dp(context), 4.dp(context))
        val params: FlexboxLayout.LayoutParams = FlexboxLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        params.setMargins(0, 4.dp(context), 4.dp(context), 4.dp(context))
        tagView.setLayoutParams(params)
        tagView.text = tag
        addView(tagView)
    }
}