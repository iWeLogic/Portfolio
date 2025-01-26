package com.iwelogic.profile.data.datasource

import com.iwelogic.core.base.datasource.*
import com.iwelogic.profile.data.dto.*
import com.iwelogic.profile.data.remote.*
import javax.inject.Inject

class ProfileDataSource @Inject constructor(private val api: ProfileApi) : BaseDataSource() {

    suspend fun getProfile(): Result<ProfileDto> {
        return getResponse(request = { api.getProfile() })
    }
}