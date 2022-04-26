package com.iwelogic.presentation.ui.main.info

import com.iwelogic.domain.main.info.InfoRepository
import com.iwelogic.domain.main.info.InfoUseCase
import com.iwelogic.domain.main.info.InfoUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object InfoUseCaseModule {

    @Provides
    fun provideNewsUseCase(infoRepository: InfoRepository): InfoUseCase {
        return InfoUseCaseImp(infoRepository)
    }
}