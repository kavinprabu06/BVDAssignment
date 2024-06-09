package com.example.bvdtest.featureDashBoard.domain

import com.example.bvdtest.utils.common.SharedPrefManager
import javax.inject.Inject

class CheckTokenValidityUseCase @Inject constructor(private val sharedPrefManager: SharedPrefManager) {
    fun execute(): Boolean {
        // Implement your logic to check token validity from shared preferences
        val token = sharedPrefManager.getAccessToken()
        return !token.isNullOrEmpty()
    }
}