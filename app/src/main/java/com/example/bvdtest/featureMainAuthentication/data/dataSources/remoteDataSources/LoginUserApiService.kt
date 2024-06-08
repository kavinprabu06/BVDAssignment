package com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources

import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query


interface LoginUserApiService {

    @POST("api/verifyUser")
    suspend fun verifyUser(
        @Query("username") userName: String,
        @Query("password") passWord: String
    ) : Response<LoginUserResponse>


}