package com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FuelSiteSalesTaxe(
    val created_at: String,
    val id: Int,
    val name: String,
    val state_id: Int,
    val updated_at: String,
    val value: Int
) : Parcelable