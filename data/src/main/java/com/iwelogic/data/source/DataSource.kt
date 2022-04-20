package com.iwelogic.data.source

import com.iwelogic.data.models.DataApp
import com.iwelogic.data.models.DataNews
import com.iwelogic.data.models.DataRegister
import com.iwelogic.data.models.DataSignIn
import com.iwelogic.data.models.DataUser
import com.iwelogic.domain.models.Result

interface DataSource {

    suspend fun register(data: DataRegister): Result<DataUser>

    suspend fun login(data: DataSignIn): Result<DataUser>

    suspend fun resendEmailConfirmation(email: String?): Result<Void>

    suspend fun getNews(pageSize: Int, offset: Int): Result<List<DataNews>>

    suspend fun getApps(): Result<List<DataApp>>
}