package com.example.bvdtest.featureDashBoard.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.DataX
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import com.example.bvdtest.featureDashBoard.domain.FuelSitesUseCase
import com.example.bvdtest.featureDashBoard.domain.UserProfileDetailUseCase
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.LoginUserResponse
import com.example.bvdtest.featureMainAuthentication.data.dataSources.remoteDataSources.model.UserProfileDetails
import com.example.bvdtest.utils.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FuelSiteViewModel @Inject constructor(
    private val fuelSitesUseCase: FuelSitesUseCase,
    private val userProfileDetailUseCase: UserProfileDetailUseCase
) :
    ViewModel() {

    private val _allFuelSites = MutableLiveData<Resource<FuelSiteResponse>>()
    val allFuelSites get() = _allFuelSites

    private val _selectedFuelSite = MutableLiveData<DataX>()
    val selectedFuelSite: LiveData<DataX> = _selectedFuelSite

    private val _userProfileDetails = MutableLiveData<UserProfileDetails>()
    val userProfileDetails get() = _userProfileDetails


    fun getAllFuelSites() {
        viewModelScope.launch {
            Resource.Loading()
            _allFuelSites.value = fuelSitesUseCase.getAllFuelSites()
        }
    }

    fun selectFuelSite(fuelSite: DataX) {
        _selectedFuelSite.value = fuelSite
    }

    suspend fun getUserProfileDetails(){
        _userProfileDetails.value = userProfileDetailUseCase.getUserProfileDetails()
    }

    fun logoutUser(){
        userProfileDetailUseCase.logoutUser()
    }
}