package com.iwelogic.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class FeedbackPresentation(

    val created: Long? = null,
    var type: CellType = CellType.SIMPLE,
    var name: String? = null,
    var rating: Float? = null,
    var text: String? = null,
    val objectId: String? = null

) : Parcelable {
    companion object {
        fun getProgressItem() = FeedbackPresentation().apply { type = CellType.PROGRESS }
    }

    fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm  aa", Locale.getDefault())
        return sdf.format(Date(created ?: 0))
    }
}
