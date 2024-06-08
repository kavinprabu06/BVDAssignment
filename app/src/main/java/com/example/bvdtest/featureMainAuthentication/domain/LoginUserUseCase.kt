package com.example.bvdtest.featureMainAuthentication.domain

import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import com.example.bvdtest.utils.common.Resource
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val loginUserRepository: LoginUserRepository) {
    suspend fun verifyUser(userName:String, passWord: String) : Resource<LoginUserResponse>{
        return loginUserRepository.verifyUser(userName,passWord)
    }
}