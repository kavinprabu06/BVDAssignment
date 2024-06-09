package com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class DataX (
    val address: String,
    val city: String,
    val country_id: Int,
    val created_at: String,
    val deleted: Int,
    val fuel_site_amenities: List<FuelSiteAmenity>,
    val fuel_site_sales_taxes: List<FuelSiteSalesTaxe>,
    val fuel_site_taxes: FuelSiteTaxes,
    val group_number: String,
    val hours: String,
    val id: Int,
    val in_yard: Int,
    val latitude: Double,
    val latitude_direction: String,
    val longitude: Double,
    val longitude_direction: String,
    val manned: Int,
    val name: String,
    val number: String,
    val phone: String,
    val state: State,
    val state_id: Int,
    val status: Int,
    val terminal_name: String,
    val updated_at: String,
    val zip: String
) : Parcelable