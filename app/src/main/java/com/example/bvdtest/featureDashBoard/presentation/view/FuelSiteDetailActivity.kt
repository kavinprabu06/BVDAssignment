package com.example.bvdtest.featureDashBoard.presentation.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bvdtest.R
import com.example.bvdtest.databinding.ActivityFuelSiteDetailBinding
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.DataX
import java.io.Serializable

class FuelSiteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFuelSiteDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFuelSiteDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fuelSite = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("fuelSite", DataX::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("fuelSite")
        }


        fuelSite?.let {
            binding.apply {
                tvNameDetail.text = it.name
                tvstateDetail.text = it.state.name
                tvProvinceDetail.text = it.city
                tvTerminalDetail.text = it.terminal_name
            }
        }


    }
}