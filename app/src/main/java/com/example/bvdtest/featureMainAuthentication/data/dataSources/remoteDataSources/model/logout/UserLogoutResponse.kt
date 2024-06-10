package com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.logout

data class UserLogoutResponse(
    val code: Int,
    val error: Boolean,
    val message: String
)