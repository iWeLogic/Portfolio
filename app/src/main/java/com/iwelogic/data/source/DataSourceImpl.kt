package com.iwelogic.data.source

import android.content.Context
import com.google.gson.Gson
import com.iwelogic.R
import com.iwelogic.data.Api
import com.iwelogic.data.Result
import com.iwelogic.models.BaseResponse
import com.iwelogic.models.RegisterUser
import com.iwelogic.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class DataSourceImpl @Inject constructor(private val api: Api, @ApplicationContext val applicationContext: Context) : DataSource {

    override suspend fun register(email: String, password: String): Result<User> {
        return getResponse(request = { api.register(RegisterUser(email, password)) }, applicationContext.getString(R.string.something_went_wrong))
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                if (result.body() == null && result.code() != 204) {
                    Result.Error(Result.Error.Code.UNKNOWN, defaultErrorMessage)
                } else {
                    Result.Success(result.body())
                }
            } else {
                ErrorUtils.parseError(result, defaultErrorMessage)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.Error(Result.Error.Code.UNKNOWN, e.message ?: defaultErrorMessage)
        }
    }

    object ErrorUtils {

        fun parseError(response: Response<*>, defaultMessage: String): Result.Error {
            return try {
                if (response.code() == 401) {
                    Result.Error(Result.Error.Code.AUTH, defaultMessage)
                } else {
                    val responseError = Gson().fromJson(response.errorBody()!!.string(), BaseResponse::class.java)
                    Result.Error(responseError.code ?: Result.Error.Code.UNKNOWN, responseError.message ?: defaultMessage)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(Result.Error.Code.UNKNOWN, e.message ?: defaultMessage)
            }
        }
    }
}