package com.iwelogic.profile.domain.use_case

import com.iwelogic.profile.domain.models.*
import com.iwelogic.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.*

class ProfileUseCaseImp(private val repository: ProfileRepository) : ProfileUseCase {

    override suspend fun getHomeData(): Result<HomeData> = withContext(Dispatchers.IO) {
        val calls: Array<Deferred<Result<Any>>> = arrayOf(
            async { repository.getProfile() },
            async { repository.getContacts() },
            async { repository.getJobs() },
            async { repository.getStudies() }
        )
        val results = awaitAll(*calls)
        val error = results.firstOrNull { it.isFailure }
        val result: Result<HomeData> = if(error != null){
            Result.failure(error.exceptionOrNull()!!)
        } else {
            val contacts : List<ContactDomain> = (results[1].getOrNull() as List<*>).map { it as ContactDomain }
            val jobs : List<JobDomain> = (results[2].getOrNull() as List<*>).map { it as JobDomain }
            val studies : List<StudyDomain> = (results[3].getOrNull() as List<*>).map { it as StudyDomain }
            Result.success(HomeData(contacts = contacts, profile = results[0].getOrNull() as ProfileDomain, jobs = jobs, studies = studies))
        }
        result
    }
}

