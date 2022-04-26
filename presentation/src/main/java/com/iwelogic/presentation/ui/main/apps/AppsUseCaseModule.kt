package com.iwelogic.presentation.ui.main.apps

import com.iwelogic.domain.main.apps.AppsRepository
import com.iwelogic.domain.main.apps.AppsUseCase
import com.iwelogic.domain.main.apps.AppsUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object AppsUseCaseModule {

    @Provides
    fun provide(appsRepository: AppsRepository): AppsUseCase {
        return AppsUseCaseImp(appsRepository)
    }
}