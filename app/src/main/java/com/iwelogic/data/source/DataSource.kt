package com.iwelogic.data.source

import com.iwelogic.data.Result
import com.iwelogic.models.User

interface DataSource {

    suspend fun register(email: String, password: String): Result<User>

}