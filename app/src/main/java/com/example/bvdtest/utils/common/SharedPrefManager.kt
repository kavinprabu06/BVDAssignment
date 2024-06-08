package com.example.bvdtest.utils.common

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import com.example.bvdtest.utils.constants.NetworkConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefManager @Inject constructor(@ApplicationContext private val context: Context) {

    private fun getSharedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            NetworkConstants.PREFS_FILENAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveTokens(authResponse: LoginUserResponse) {
        val sharedPreferences = getSharedPreferences()
        val currentTime = System.currentTimeMillis()
        val expiryTime = currentTime + (authResponse.expires_in * 1000) // Convert to milliseconds

        sharedPreferences.edit().apply {
            putString(NetworkConstants.KEY_ACCESS_TOKEN, authResponse.access_token)
            putString(NetworkConstants.KEY_REFRESH_TOKEN, authResponse.refresh_token)
            putLong(NetworkConstants.KEY_EXPIRES_TOKEN, expiryTime)

            apply()
        }
    }

    fun getAccessToken(): String? {
        return getSharedPreferences().getString(NetworkConstants.KEY_ACCESS_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return getSharedPreferences().getString(NetworkConstants.KEY_REFRESH_TOKEN, null)
    }

    fun clearTokens() {
        val sharedPreferences = getSharedPreferences()
        sharedPreferences.edit().clear().apply()
    }
}