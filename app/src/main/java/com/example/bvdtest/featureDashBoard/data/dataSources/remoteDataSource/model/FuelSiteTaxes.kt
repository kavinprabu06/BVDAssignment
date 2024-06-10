package com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FuelSiteTaxes(
    val billable: Int?,
    val comdata_site_id: String?,
    val created_at: String?,
    val efs_site_id: String?,
    val fee_amount: Int?,
    val fet_amount: Double?,
    val fet_dyedAmount: Double?,
    val freight_rates: String?,
    val fuel_site_id: Int?,
    val id: Int?,
    val local_tax_1_amount: String?,
    val local_tax_2_amount: String?,
    val network: Int?,
    val number: String?,
    val other_cad_tax: Int?,
    val other_us_tax: Int?,
    val pct_amount: Double?,
    val pct_dyedAmount: Double?,
    val pft_amount: Double?,
    val pft_dyedAmount: Double?,
    val quikq_site_id: String?,
    val tchek_site_id: String?,
    val terminal_id: Int?,
    val updated_at: String?
) : Parcelable