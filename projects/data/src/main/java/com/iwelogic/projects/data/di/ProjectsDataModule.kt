package com.iwelogic.projects.data.di

import com.iwelogic.projects.data.datasource.*
import com.iwelogic.projects.data.remote.*
import com.iwelogic.projects.data.repository.*
import com.iwelogic.projects.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.iwelogic.core.BuildConfig.BACKEND_URL

@InstallIn(SingletonComponent::class)
@Module
object ProjectsDataModule {

    @Provides
    @Singleton
    fun provideProjectsApi(builder: Retrofit.Builder, client: OkHttpClient): ProjectsApi {
        return builder
            .baseUrl(BACKEND_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProjectsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProjectsRepository(projectsDataSource: ProjectsDataSource): ProjectsRepository {
        return ProjectsRepositoryImp(projectsDataSource)
    }
}
