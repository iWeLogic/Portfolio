package com.iwelogic.portfolio.data.source

import com.google.gson.Gson
import com.iwelogic.portfolio.data.Api
import com.iwelogic.portfolio.domain.main.models.Result
import com.iwelogic.portfolio.domain.main.models.BaseResponse
import com.iwelogic.portfolio.domain.main.models.RegisterData
import com.iwelogic.portfolio.domain.main.models.SignInData
import com.iwelogic.portfolio.domain.main.models.User
import retrofit2.Response
import javax.inject.Inject

class DataSourceImp @Inject constructor(private val api: Api) : DataSource {

    override suspend fun register(data: RegisterData): Result<User> {
        return getResponse(request = { api.register(data) })
    }

    override suspend fun login(data: SignInData): Result<User> {
        return getResponse(request = { api.login(data) })
    }

    override suspend fun resendEmailConfirmation(email: String?): Result<Void> {
        return getResponse(request = { api.resendEmailConfirmation(email) })
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun <T> getResponse(request: suspend () -> Response<T>): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                Result.Success(result.body())
            } else {
                return try {
                    val responseError = Gson().fromJson(result.errorBody()?.string(), BaseResponse::class.java)
                    Result.Error(responseError.code ?: Result.Error.Code.UNKNOWN, responseError.message)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Result.Error(Result.Error.Code.UNKNOWN, e.message)
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(Result.Error.Code.UNKNOWN, e.message)
        }
    }
}