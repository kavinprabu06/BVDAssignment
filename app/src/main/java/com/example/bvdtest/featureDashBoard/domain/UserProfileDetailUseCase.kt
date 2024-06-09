package com.example.bvdtest.featureDashBoard.domain

import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.UserProfileDetails
import com.example.bvdtest.utils.common.Resource
import javax.inject.Inject

class UserProfileDetailUseCase @Inject constructor(private val fuelSiteRepository: FuelSiteRepository) {
    suspend fun getUserProfileDetails(): UserProfileDetails{
        return fuelSiteRepository.getUserProfileDetails()
    }

     fun logoutUser(){
        return fuelSiteRepository.logoutUser()
    }
}