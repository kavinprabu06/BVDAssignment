package com.example.bvdtest.featureDashBoard.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.FuelSiteResponse
import com.example.bvdtest.featureDashBoard.domain.FuelSitesUseCase
import com.example.bvdtest.utils.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FuelSiteViewModel @Inject constructor(private val fuelSitesUseCase: FuelSitesUseCase) :
    ViewModel() {

    private val _allFuelSites = MutableLiveData<Resource<FuelSiteResponse>>()
    val allFuelSites get() = _allFuelSites


    fun getAllFuelSites() {
        viewModelScope.launch {
            Resource.Loading()
            allFuelSites.value = fuelSitesUseCase.getAllFuelSites()
        }
    }
}