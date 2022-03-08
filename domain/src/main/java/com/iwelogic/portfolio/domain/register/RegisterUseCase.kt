package com.iwelogic.portfolio.domain.register

import com.iwelogic.portfolio.data.models.RegisterData
import com.iwelogic.portfolio.data.models.Result
import com.iwelogic.portfolio.data.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun register(data: RegisterData): Flow<Result<Any>>
}

@InstallIn(ViewModelComponent::class)
@Module
object RegisterUseCaseModule {

    @Provides
    fun provideRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCaseImp(registerRepository)
    }
}