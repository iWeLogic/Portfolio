package com.iwelogic.portfolio.presentation

import android.content.Context
import com.iwelogic.portfolio.domain.LocalUserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.ref.WeakReference

class HeaderInterceptor constructor(applicationContext: Context, val localUserRepository: LocalUserRepository) : Interceptor {

    var context: WeakReference<Context> = WeakReference(applicationContext)

    override fun intercept(chain: Interceptor.Chain): Response {
        var token: String?
        runBlocking {
            token = localUserRepository.userFlow.first().userToken
        }
        val request = chain.request().newBuilder()
            .header("Accept", "application/json")
        token?.let {
            if (it.isNotEmpty())
                request.header("user-token", it)
        }
        return chain.proceed(request.build())
    }
}