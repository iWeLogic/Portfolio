package com.iwelogic.data

import android.content.Context
import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat.getLongVersionCode
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.ref.WeakReference

class HeaderInterceptor constructor(context: Context) : Interceptor {

    var context: WeakReference<Context> = WeakReference(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        var versionName: String? = null
        var versionCode: Long? = null
        try {
            val pInfo: PackageInfo? = context.get()?.packageManager?.getPackageInfo(context.get()?.packageName ?: "", 0)
            versionName = pInfo?.versionName?.toString()
            versionCode = getLongVersionCode(pInfo!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val request = chain.request().newBuilder()
            .header("CLIENT_APP_PLATFORM", "Android")
            .header("CLIENT_APP_VERSION", "$versionName ($versionCode)")
            .header("Accept", "application/json")
        /*context.get()?.readObject(USER, User::class.java)?.token?.let {
            request.header("Authorization", "Bearer $it")
        }*/
        return chain.proceed(request.build())
    }
}