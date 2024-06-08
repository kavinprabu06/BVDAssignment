package com.example.bvdtest.featureDashBoard.domain

import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import com.example.bvdtest.utils.common.Resource

interface FuelSiteRepository {
    suspend fun getAllFuelSites() : Resource<FuelSiteResponse>
}