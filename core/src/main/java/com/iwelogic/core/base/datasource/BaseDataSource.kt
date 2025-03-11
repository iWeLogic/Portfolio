package com.iwelogic.core.base.datasource

import com.google.gson.*
import com.iwelogic.core.base.models.*
import retrofit2.*
import java.net.*

open class BaseDataSource {

    suspend fun <T> getResponse(request: suspend () -> Response<T>): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return try {
                    Result.success(result.body()!!)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            } else {
                return try {
                    val responseError = Gson().fromJson(result.errorBody()?.string(), BaseResponse::class.java)
                    Result.failure(
                        AppFailure.ResponseFailure(message = responseError.message)
                    )
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        } catch (e: Throwable) {
            when (e) {
                is UnknownHostException -> Result.failure(e)
                else -> Result.failure(UnknownError(e.message))
            }
        }
    }
}

