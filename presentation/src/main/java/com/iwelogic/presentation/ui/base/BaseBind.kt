package com.iwelogic.presentation.ui.base

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.*
import com.iwelogic.core.utils.dp
import com.iwelogic.presentation.R

@BindingAdapter("html")
fun TextView.seHtml(html: String?) {
    val spanned: Spanned = HtmlCompat.fromHtml(html ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
    val spannableBuilder = SpannableStringBuilder(spanned)
    text = spannableBuilder
    movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter("image", "scaleType", "radius", requireAll = false)
fun ImageView.setImage(image: String?, scaleType: ScaleType?, radius: Int?) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 4.dp(context).toFloat()
    circularProgressDrawable.centerRadius = 12.dp(context).toFloat()
    circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(context, R.color.headerTextColor))
    circularProgressDrawable.start()
    image?.let {
        Glide.with(context).load(image).transform(
            if (radius != null && radius > 0) {
                when (scaleType) {
                    ScaleType.CENTER_INSIDE -> MultiTransformation(CenterInside(), RoundedCorners(radius.dp(context)))
                    ScaleType.FIT_CENTER -> MultiTransformation(FitCenter(), RoundedCorners(radius.dp(context)))
                    ScaleType.CIRCLE_CROP -> MultiTransformation(CircleCrop(), RoundedCorners(radius.dp(context)))
                    else -> MultiTransformation(CenterCrop(), RoundedCorners(radius.dp(context)))
                }
            } else {
                when (scaleType) {
                    ScaleType.CENTER_INSIDE -> CenterInside()
                    ScaleType.FIT_CENTER -> FitCenter()
                    ScaleType.CIRCLE_CROP -> CircleCrop()
                    else -> CenterCrop()
                }
            }
        ).placeholder(circularProgressDrawable).into(this)
    }
}

enum class ScaleType {
    CENTER_CROP,
    CENTER_INSIDE,
    FIT_CENTER,
    CIRCLE_CROP
}