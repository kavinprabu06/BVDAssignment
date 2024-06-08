package com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource

import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface sitesApiService {

    @GET("api/allFuelSites")
    suspend fun getFuelSites(
        @Query("page_length") pageLength: Int,
        @Query("page") page: Int,
        @Query("keyword") keyword: String,
        @Query("sorting_field") sortingField: String,
        @Query("sorting_direction") sortingDirection: String
    ): Response<FuelSiteResponse>
}