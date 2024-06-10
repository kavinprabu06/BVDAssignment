package com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource

import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.logout.UserLogoutResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("api/logout")
    suspend fun logOutUser(): Response<UserLogoutResponse>
}