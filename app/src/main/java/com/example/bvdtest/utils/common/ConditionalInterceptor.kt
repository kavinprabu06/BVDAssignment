package com.example.bvdtest.utils.common

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConditionalInterceptor @Inject constructor(private val sharedPrefManager: SharedPrefManager) :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPrefManager.getAccessToken()

        val originalRequest = chain.request()
        val newRequest = if (originalRequest.url.encodedPath.contains("verifyUser")) {
            originalRequest.newBuilder()
                .addHeader("Content-Type","application/json")
                .build()
        } else {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type","application/json")
                .build()
        }
        return chain.proceed(newRequest)
    }
}