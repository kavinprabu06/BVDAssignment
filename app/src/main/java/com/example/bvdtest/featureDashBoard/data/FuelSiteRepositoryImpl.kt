package com.example.bvdtest.featureDashBoard.data

import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.sitesApiService
import com.example.bvdtest.featureDashBoard.domain.FuelSiteRepository
import com.example.bvdtest.utils.common.Resource
import com.example.bvdtest.utils.constants.NetworkConstants
import java.io.IOException
import javax.inject.Inject

class FuelSiteRepositoryImpl @Inject constructor(private val sitesApiService: sitesApiService) :
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
}

