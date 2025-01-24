package com.iwelogic.projects.presentation.di

import com.iwelogic.projects.domain.repository.*
import com.iwelogic.projects.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.*

@InstallIn(ViewModelComponent::class)
@Module
object ProjectUseCaseModule {

    @Provides
    fun provide(projectsRepository: ProjectsRepository): ProjectsUseCase {
        return ProjectsUseCaseImp(projectsRepository)
    }
}