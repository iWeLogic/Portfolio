package com.iwelogic.portfolio.presentation.base

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
object StringHolderModule {

    @Provides
    fun provideStringHolder(@ApplicationContext applicationContext: Context): StringHolder {
        return StringHolderImp(applicationContext)
    }
}