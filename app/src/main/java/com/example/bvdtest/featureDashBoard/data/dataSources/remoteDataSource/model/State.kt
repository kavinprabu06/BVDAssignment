package com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class State(
    val abbreviation: String,
    val country_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
) : Parcelable