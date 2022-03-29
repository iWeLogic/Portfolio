package com.iwelogic.portfolio.core.utils

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import com.google.gson.Gson
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.coroutineContext

fun List<*>?.deepEquals(other: List<*>?): Boolean {
    if (this == null && other != null || this != null && other == null) return false
    if (this == null && other == null) return true
    return this!!.size == other!!.size && this.mapIndexed { index, element -> element == other[index] }.all { it }
}

inline fun <reified T> Gson.deepCopy(value: T): T {
    return fromJson(toJson(value), T::class.java)
}

inline fun <reified T> List<T>?.deepCopy(): List<T>? {
    val temp: MutableList<T> = ArrayList()
    val gson = Gson()
    this?.forEach {
        temp.add(gson.fromJson(gson.toJson(it), T::class.java))
    }
    return if (this == null) null else temp.toList()
}

fun Boolean?.isTrue(action: () -> Unit) {
    if (this == true) {
        action.invoke()
    }
}

fun Boolean?.isTrue(): Boolean {
    return this == true
}

fun Int?.value(): Int {
    return this ?: 0
}

suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
    collect {
        coroutineContext.ensureActive()
        action(it)
    }
}

inline fun catchAll(action: () -> Unit) {
    try {
        action()
    } catch (t: Throwable) {
        t.printStackTrace()
        Log.w("myLog", "catchAll: " + t.message)
    }
}

fun View.getAllChildren(): List<View> {
    val result = ArrayList<View>()
    if (this !is ViewGroup) {
        result.add(this)
    } else {
        for (index in 0 until this.childCount) {
            val child = this.getChildAt(index)
            result.addAll(child.getAllChildren())
        }
    }
    return result
}

fun View.removeAccessibilityFocus() {
    getAllChildren().forEach {
        it.performAccessibilityAction(AccessibilityNodeInfo.ACTION_CLEAR_ACCESSIBILITY_FOCUS, null)
    }
}

fun View.accessibilityStatus(status: Boolean) {
    getAllChildren().forEach {
        it.importantForAccessibility = if (status) View.IMPORTANT_FOR_ACCESSIBILITY_YES else View.IMPORTANT_FOR_ACCESSIBILITY_NO
    }
}

inline fun <reified T> ViewGroup.getFirstChildByType(): T? {
    val foundChild: T? = null
    val childrenCount = childCount
    Log.w("myLog", "getFirstChildByType: 0")
    for (i in 0..childrenCount) {
        val view = getChildAt(i)
        Log.w("myLog", "getFirstChildByType: 1")
        return null
    }
    Log.w("myLog", "getFirstChildByType: 2")
    return foundChild
}


