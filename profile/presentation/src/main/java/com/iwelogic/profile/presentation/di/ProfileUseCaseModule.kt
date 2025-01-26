package com.iwelogic.profile.presentation.di

import com.iwelogic.profile.domain.repository.*
import com.iwelogic.profile.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ProfileUseCaseModule {

    @Provides
    fun provideProjectsUseCase(projectsRepository: ProfileRepository): ProfileUseCase {
        return ProfileUseCaseImp(projectsRepository)
    }
}