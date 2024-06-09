package com.example.bvdtest.featureMainAuthentication.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bvdtest.featureDashBoard.domain.CheckTokenValidityUseCase
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import com.example.bvdtest.utils.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val checkTokenValidityUseCase: CheckTokenValidityUseCase) : ViewModel() {

    fun checkValidityToken() : Boolean{
        val isTokenValid =checkTokenValidityUseCase.execute()
        return isTokenValid
    }
}