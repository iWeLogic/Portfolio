package com.iwelogic.portfolio.core.utils


import android.graphics.Paint
import android.text.TextUtils

private fun splitIntoStringsThatFit(source: String, maxWidthPx: Float, paint: Paint): List<String> {
    if (TextUtils.isEmpty(source) || paint.measureText(source) <= maxWidthPx) {
        return listOf(source)
    }
    val result: ArrayList<String> = ArrayList()
    var start = 0
    for (i in 1..source.length) {
        val subStr = source.substring(start, i)
        if (paint.measureText(subStr) >= maxWidthPx) {
            val fits = source.substring(start, i - 1)
            result.add(fits)
            start = i - 1
        }
        if (i == source.length) {
            val fits = source.substring(start, i)
            result.add(fits)
        }
    }
    return result
}

private fun processFitChunk(maxWidth: Float, paint: Paint, result: ArrayList<String>, currentLine: ArrayList<String?>, chunk: String) {
    currentLine.add(chunk)
    val currentLineStr = TextUtils.join(" ", currentLine)
    if (paint.measureText(currentLineStr) >= maxWidth) {
        currentLine.removeAt(currentLine.size - 1)
        result.add(TextUtils.join(" ", currentLine))
        currentLine.clear()
        currentLine.add(chunk)
    }
}