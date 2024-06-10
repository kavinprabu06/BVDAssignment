package com.example.bvdtest.featureDashBoard.domain

import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.UserProfileDetails
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.logout.UserLogoutResponse
import com.example.bvdtest.utils.common.Resource

interface FuelSiteRepository {
    suspend fun getAllFuelSites() : Resource<FuelSiteResponse>
    suspend fun getUserProfileDetails(): UserProfileDetails

    suspend fun logoutUser() : Resource<UserLogoutResponse>?
}