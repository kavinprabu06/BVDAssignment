package com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model

data class FuelSiteResponse(
    val code: Int,
    val `data`: Data,
    val error: Boolean,
    val message: String
)