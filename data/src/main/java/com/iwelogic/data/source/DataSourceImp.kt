package com.iwelogic.data.source

import com.google.gson.Gson
import com.iwelogic.data.Api
import com.iwelogic.data.models.*
import com.iwelogic.domain.models.BaseResponse
import com.iwelogic.domain.models.Result
import retrofit2.Response
import java.net.UnknownHostException

class DataSourceImp(private val api: Api) : DataSource {

    override suspend fun register(data: RegisterData): Result<Any> {
        return getResponse(request = { api.register(data) })
    }

    override suspend fun updateUser(data: UserData): Result<UserData> {
        return getResponse(request = { api.updateUser(data) })
    }

    override suspend fun getUser(objectId: String?): Result<List<UserData>> {
        return getResponse(request = { api.getUser("objectId = '$objectId'") })
    }

    override suspend fun getFeedbacks(pageSize: Int, offset: Int): Result<List<FeedbackData>> {
        return getResponse(request = { api.getFeedbacks(pageSize, offset) })
    }

    override suspend fun addFeedback(data: FeedbackData): Result<FeedbackData> {
        return getResponse(request = { api.addFeedback(data) })
    }

    override suspend fun remember(email: String): Result<Void> {
        return getResponse(request = { api.remember(email) })
    }

    override suspend fun login(data: SignInData): Result<UserData> {
        return getResponse(request = { api.login(data) })
    }

    override suspend fun resendEmailConfirmation(email: String?): Result<Void> {
        return getResponse(request = { api.resendEmailConfirmation(email) })
    }

    override suspend fun getNews(pageSize: Int, offset: Int): Result<List<NewsData>> {
        return getResponse(request = { api.getNews(pageSize, offset) })
    }

    override suspend fun getApps(): Result<List<AppData>> {
        return getResponse(request = { api.getApps() })
    }

    override suspend fun getInfo(): Result<InfoData> {
        return getResponse(request = { api.getInfo() })
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
            when (e) {
                is UnknownHostException -> Result.Error(Result.Error.Code.NO_CONNECTION)
                else -> Result.Error(Result.Error.Code.UNKNOWN, e.message)
            }
        }
    }
}