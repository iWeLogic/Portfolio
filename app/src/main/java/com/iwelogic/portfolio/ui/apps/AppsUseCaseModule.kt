package com.iwelogic.portfolio.ui.apps

import com.iwelogic.portfolio.domain.apps.AppsRepository
import com.iwelogic.portfolio.domain.apps.AppsUseCase
import com.iwelogic.portfolio.domain.apps.AppsUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object AppsUseCaseModule {

    @Provides
    fun provideAppsUseCase(appsRepository: AppsRepository): AppsUseCase {
        return AppsUseCaseImp(appsRepository)
    }
}