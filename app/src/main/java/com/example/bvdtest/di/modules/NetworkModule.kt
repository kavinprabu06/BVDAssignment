package com.example.bvdtest.di.modules

import android.content.Context
import com.example.bvdtest.featureDashBoard.data.FuelSiteRepositoryImpl
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.sitesApiService
import com.example.bvdtest.featureDashBoard.domain.FuelSiteRepository
import com.example.bvdtest.featureDashBoard.domain.FuelSitesUseCase
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.LoginUserApiService
import com.example.bvdtest.featureMainAuthentication.data.repositoryImpl.LoginUserRepositoryImpl
import com.example.bvdtest.featureMainAuthentication.domain.LoginUserRepository
import com.example.bvdtest.featureMainAuthentication.domain.LoginUserUseCase
import com.example.bvdtest.utils.common.SharedPrefManager
import com.example.bvdtest.utils.common.ConditionalInterceptor
import com.example.bvdtest.utils.constants.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideConditionalInterceptor(sharedPrefManager: SharedPrefManager): Interceptor {
        return ConditionalInterceptor(sharedPrefManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        conditionalInterceptor: ConditionalInterceptor
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(conditionalInterceptor)
            .addInterceptor(interceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext app: Context): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideLoginUserApiService(retrofit: Retrofit): LoginUserApiService {
        return retrofit.create(LoginUserApiService::class.java)
    }

    @Provides
    fun provideLoginUserRepository(loginUserApiService: LoginUserApiService, sharedPrefManager: SharedPrefManager): LoginUserRepository {
        return LoginUserRepositoryImpl(loginUserApiService, sharedPrefManager)
    }

    @Provides
    fun provideLoginUserUseCase(loginUserRepository: LoginUserRepository) : LoginUserUseCase{
        return LoginUserUseCase(loginUserRepository)
    }

    @Provides
    @Singleton
    fun provideFuelSiteApiService(retrofit: Retrofit): sitesApiService{
        return retrofit.create(sitesApiService::class.java)
    }

    @Provides
    fun provideFuelSiteRepository(sitesApiService: sitesApiService): FuelSiteRepository {
        return FuelSiteRepositoryImpl(sitesApiService)
    }

    @Provides
    fun provideFuelSiteUserUseCase(fuelSiteRepository: FuelSiteRepository) : FuelSitesUseCase{
        return FuelSitesUseCase(fuelSiteRepository)
    }

}