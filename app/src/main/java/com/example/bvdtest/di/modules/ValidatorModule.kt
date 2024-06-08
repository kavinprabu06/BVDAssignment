package com.example.bvdtest.di.modules

import com.example.bvdtest.featureMainAuthentication.domain.validation.PassWordValidatorUseCase
import com.example.bvdtest.featureMainAuthentication.domain.validation.UserNameValidatorUseCase
import com.example.bvdtest.featureMainAuthentication.domain.validation.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ValidatorModule {

    @Provides
    fun provideUserNameValidtorUseCase() : Validator<String> {
        return UserNameValidatorUseCase()
    }

    @Provides
    fun providePasswordValidtorUseCase() : Validator<String>{
        return PassWordValidatorUseCase()
    }
}