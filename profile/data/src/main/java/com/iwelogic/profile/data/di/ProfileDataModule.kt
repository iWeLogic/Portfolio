package com.iwelogic.profile.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.iwelogic.core.BuildConfig.BACKEND_URL
import com.iwelogic.profile.data.datasource.*
import com.iwelogic.profile.data.remote.*
import com.iwelogic.profile.data.repository.*
import com.iwelogic.profile.domain.repository.*

@InstallIn(SingletonComponent::class)
@Module
object ProfileDataModule {

    @Provides
    @Singleton
    fun provideProfileApi(builder: Retrofit.Builder, client: OkHttpClient): ProfileApi {
        return builder
            .baseUrl(BACKEND_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileDataSource: ProfileDataSource): ProfileRepository {
        return ProfileRepositoryImp(profileDataSource)
    }
}
