package com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FuelSiteAmenity(
    val created_at: String,
    val fuel_site_id: Int,
    val id: Int,
    val quantity: String,
    val site_amenity_id: Int,
    val updated_at: String
) : Parcelable