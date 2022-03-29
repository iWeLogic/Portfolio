package com.iwelogic.portfolio.presentation.base

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.*
import com.iwelogic.portfolio.domain.utils.dp

object BaseBind {

    @BindingAdapter("html")
    @JvmStatic
    fun seHtml(view: TextView, html: String?) {
        val spanned: Spanned = HtmlCompat.fromHtml(html ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
        val spannableBuilder = SpannableStringBuilder(spanned)
        view.text = spannableBuilder
        view.movementMethod = LinkMovementMethod.getInstance()
    }


    @BindingAdapter("image", "scaleType", "radius", requireAll = false)
    @JvmStatic
    fun setImage(view: ImageView, image: String?, scaleType: ScaleType?, radius: Int?) {
        image?.let {
            Glide.with(view.context).load(image).transform(
                if (radius != null && radius > 0) {
                    when (scaleType) {
                        ScaleType.CENTER_INSIDE -> MultiTransformation(CenterInside(), RoundedCorners(radius.dp(view.context)))
                        ScaleType.FIT_CENTER -> MultiTransformation(FitCenter(), RoundedCorners(radius.dp(view.context)))
                        ScaleType.CIRCLE_CROP -> MultiTransformation(CircleCrop(), RoundedCorners(radius.dp(view.context)))
                        else -> MultiTransformation(CenterCrop(), RoundedCorners(radius.dp(view.context)))
                    }
                } else {
                    when (scaleType) {
                        ScaleType.CENTER_INSIDE -> CenterInside()
                        ScaleType.FIT_CENTER -> FitCenter()
                        ScaleType.CIRCLE_CROP -> CircleCrop()
                        else -> CenterCrop()
                    }
                }
            ).into(view)
        }
    }

    enum class ScaleType {
        CENTER_CROP,
        CENTER_INSIDE,
        FIT_CENTER,
        CIRCLE_CROP
    }
}