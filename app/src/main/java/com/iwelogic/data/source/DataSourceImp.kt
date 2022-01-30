package com.iwelogic.data.source

import android.content.Context
import com.google.gson.Gson
import com.iwelogic.R
import com.iwelogic.data.Api
import com.iwelogic.data.Result
import com.iwelogic.models.RegisterData
import com.iwelogic.models.BaseResponse
import com.iwelogic.models.SignInData
import com.iwelogic.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class DataSourceImp @Inject constructor(private val api: Api, @ApplicationContext val applicationContext: Context) : DataSource {

    override suspend fun register(data: RegisterData): Result<User> {
        return getResponse(request = { api.register(data) }, applicationContext.getString(R.string.something_went_wrong))
    }

    override suspend fun login(data: SignInData): Result<User> {
        return getResponse(request = { api.login(data) }, applicationContext.getString(R.string.something_went_wrong))
    }

    override suspend fun resendEmailConfirmation(email: String?): Result<Void> {
        return getResponse(request = { api.resendEmailConfirmation(email) }, applicationContext.getString(R.string.something_went_wrong))
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                Result.Success(result.body())
            } else {
                return try {
                    val responseError = Gson().fromJson(result.errorBody()?.string(), BaseResponse::class.java)
                    Result.Error(responseError.code ?: Result.Error.Code.UNKNOWN, responseError.message ?: defaultErrorMessage)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Result.Error(Result.Error.Code.UNKNOWN, e.message ?: defaultErrorMessage)
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(Result.Error.Code.UNKNOWN, e.message ?: defaultErrorMessage)
        }
    }
}