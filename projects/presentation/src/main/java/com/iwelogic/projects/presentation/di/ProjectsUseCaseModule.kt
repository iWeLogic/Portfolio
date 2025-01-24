package com.iwelogic.projects.presentation.di

import com.iwelogic.projects.domain.repository.*
import com.iwelogic.projects.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ProjectsUseCaseModule {

    @Provides
    fun provideProjectsUseCase(projectsRepository: ProjectsRepository): ProjectsUseCase {
        return ProjectsUseCaseImp(projectsRepository)
    }
}