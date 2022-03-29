package com.iwelogic.portfolio.data.source

import com.iwelogic.portfolio.data.models.DataApp
import com.iwelogic.portfolio.data.models.DataNews
import com.iwelogic.portfolio.data.models.DataRegister
import com.iwelogic.portfolio.data.models.DataSignIn
import com.iwelogic.portfolio.data.models.DataUser
import com.iwelogic.portfolio.domain.models.Result

interface DataSource {

    suspend fun register(data: DataRegister): Result<DataUser>

    suspend fun login(data: DataSignIn): Result<DataUser>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>

    suspend fun getNews(pageSize: Int, offset: Int): Result<List<DataNews>>

    suspend fun getApps(): Result<List<DataApp>>
}