package com.example.bvdtest.featureMainAuthentication.domain

import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import com.example.bvdtest.utils.common.Resource

interface LoginUserRepository {
    suspend fun verifyUser(userName:String, passWord: String): Resource<LoginUserResponse>
//    fun saveAuthToken(authResponse: LoginUserResponse)
//    fun getAuthToken(): String?
//    fun isTokenExpired(): Boolean
//    suspend fun refreshToken(): String?
}