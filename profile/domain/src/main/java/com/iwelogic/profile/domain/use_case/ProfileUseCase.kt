package com.iwelogic.profile.domain.use_case

import com.iwelogic.profile.domain.models.*

interface ProfileUseCase {

    suspend fun getHomeData(): Result<HomeData>
}

