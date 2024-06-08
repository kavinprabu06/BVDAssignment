package com.example.bvdtest.featureDashBoard.domain

import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import com.example.bvdtest.utils.common.Resource
import javax.inject.Inject

class FuelSitesUseCase @Inject constructor(private val fuelSiteRepository: FuelSiteRepository) {
    suspend fun getAllFuelSites(): Resource<FuelSiteResponse> {
        return fuelSiteRepository.getAllFuelSites()
    }

}