package com.iwelogic.portfolio.data.source

import android.content.Context
import com.google.gson.Gson
import com.iwelogic.portfolio.data.R
import com.iwelogic.portfolio.data.models.DataApp
import com.iwelogic.portfolio.data.models.DataNews
import com.iwelogic.portfolio.data.models.DataRegister
import com.iwelogic.portfolio.data.models.DataSignIn
import com.iwelogic.portfolio.data.models.DataUser
import com.iwelogic.portfolio.domain.models.BaseResponse
import com.iwelogic.portfolio.presentation.Api
import retrofit2.Response
import java.net.UnknownHostException
import com.iwelogic.portfolio.domain.models.Result

class DataSourceImp (private val api: Api, val context: Context) : DataSource {

    override suspend fun register(data: DataRegister): Result<DataUser> {
        return getResponse(request = { api.register(data) })
    }

    override suspend fun login(data: DataSignIn): Result<DataUser> {
        return getResponse(request = { api.login(data) })
    }

    override suspend fun resendEmailConfirmation(email: String?): Result<Void> {
        return getResponse(request = { api.resendEmailConfirmation(email) })
    }

    override suspend fun getNews(pageSize: Int, offset: Int): Result<List<DataNews>> {
        return getResponse(request = { api.getNews(pageSize, offset) })
    }

    override suspend fun getApps(): Result<List<DataApp>> {
        return getResponse(request = { api.getApps() })
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun <T> getResponse(request: suspend () -> Response<T>): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                com.iwelogic.portfolio.domain.models.Result.Success(result.body())
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
            when (e) {
                is UnknownHostException -> Result.Error(Result.Error.Code.NO_CONNECTION, context.getString(R.string.make_sure_internet_is_turned_on))
                else -> Result.Error(Result.Error.Code.UNKNOWN, e.message)
            }
        }
    }
}