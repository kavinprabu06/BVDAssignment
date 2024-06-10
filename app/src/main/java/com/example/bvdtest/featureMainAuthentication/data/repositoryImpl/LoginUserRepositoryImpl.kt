package com.example.bvdtest.featureMainAuthentication.data.repositoryImpl

import android.util.Log
import android.widget.Toast
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.LoginUserApiService
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import com.example.bvdtest.featureMainAuthentication.domain.LoginUserRepository
import com.example.bvdtest.utils.common.Resource
import com.example.bvdtest.utils.common.SharedPrefManager
import java.io.IOException
import javax.inject.Inject

class LoginUserRepositoryImpl @Inject constructor(
    private val loginUserApiService: LoginUserApiService,
    private val sharedPreferencesManager: SharedPrefManager
) :
    LoginUserRepository {

    override suspend fun verifyUser(
        userName: String,
        passWord: String
    ): Resource<LoginUserResponse> {
        try {
            val loginUserResponse = loginUserApiService.verifyUser(userName, passWord)
            return if (loginUserResponse.isSuccessful) {

                loginUserResponse.body()?.let { response ->
                    if (response.access_token.isNullOrEmpty()){
                        return Resource.Failure(" " +loginUserResponse.toString())
                    }

                    sharedPreferencesManager.saveTokens(response)
                    Resource.Success(response)
                } ?: Resource.Failure("Response in empty")

            } else {
                Log.d("LoginResponse bbbbb", ""+ loginUserResponse.body().toString())
                Resource.Failure(loginUserResponse.message())

            }
        } catch (io: IOException) {

            return Resource.Failure(io.message.toString())

        } catch (e: Exception) {

            return Resource.Failure(e.message.toString())
        }
    }
}