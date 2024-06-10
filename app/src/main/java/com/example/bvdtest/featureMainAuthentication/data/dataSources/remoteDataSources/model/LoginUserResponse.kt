package com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model

data class LoginUserResponse(
    val access_token: String?,
    val email: String,
    val expires_in: Int,
    val first_name: String,
    val last_name: String,
    val phone: String,
    val refresh_token: String?,
    val term_accepted: Int,
    val token_type: String,
    val twofactor_accepted: Int,
    val user_id: Int
)