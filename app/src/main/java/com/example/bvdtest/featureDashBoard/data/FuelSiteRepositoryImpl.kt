package com.example.bvdtest.featureDashBoard.data

import android.util.Log
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.sitesApiService
import com.example.bvdtest.featureDashBoard.domain.FuelSiteRepository
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.UserProfileDetails
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.logout.UserLogoutResponse
import com.example.bvdtest.utils.common.Resource
import com.example.bvdtest.utils.common.SharedPrefManager
import com.example.bvdtest.utils.constants.NetworkConstants
import java.io.IOException
import java.lang.IllegalStateException
import javax.inject.Inject

class FuelSiteRepositoryImpl @Inject constructor(
    private val sitesApiService: sitesApiService,
    private val sharedPrefManager: SharedPrefManager
) :
    FuelSiteRepository {
    override suspend fun getAllFuelSites(): Resource<FuelSiteResponse> {

        try {
            val fuelSiteResponse = sitesApiService.getFuelSites(
                NetworkConstants.PARAM_PAGE_LENGTH,
                NetworkConstants.PARAM_PAGE,
                NetworkConstants.PARAM_KEYWORD,
                NetworkConstants.PARAM_SORTING_FIELD,
                NetworkConstants.PARAM_SORTING_DIRECTION
            )
            return if (fuelSiteResponse.isSuccessful) {
                fuelSiteResponse.body()?.let { response ->
                    Resource.Success(response)
                } ?: Resource.Failure("Response in empty")

            } else {
                Resource.Failure(fuelSiteResponse.message())
            }
        } catch (io: IOException) {
            return Resource.Failure(io.message.toString())

        } catch (e: Exception) {
            return Resource.Failure(e.message.toString())
        }
    }

    override suspend fun getUserProfileDetails(): UserProfileDetails {
        return sharedPrefManager.getUserProfileDetails()
    }

    override suspend fun logoutUser() : Resource<UserLogoutResponse>? {
        try {
            val logoutUserResponse = sitesApiService.logOutUser()

            return if (logoutUserResponse.isSuccessful){
                logoutUserResponse.body()?.let {
                    clearSharedPreferenceDetails()
                     Resource.Success(it)
                }
            }
            else{
                Resource.Failure("Api Error")
            }
        } catch (io: IOException) {

            return Resource.Failure(io.message.toString())

        } catch (e: Exception) {

            return Resource.Failure(e.message.toString())
        }catch ( e: IllegalStateException){
            return Resource.Failure(e.message.toString())
        }

    }



    private fun clearSharedPreferenceDetails() {
        sharedPrefManager.clearSharedPreferenceDetails()
    }
}

