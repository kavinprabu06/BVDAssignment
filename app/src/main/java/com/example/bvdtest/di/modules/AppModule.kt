package com.example.bvdtest.di.modules

import android.content.Context
import com.example.bvdtest.utils.common.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSharedPreferencesManager(@ApplicationContext context: Context): SharedPrefManager {
        return SharedPrefManager(context)
    }
}