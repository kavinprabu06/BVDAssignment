package com.example.bvdtest.featureDashBoard.presentation.view.activities

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bvdtest.databinding.ActivityFuelSiteDetailBinding
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.DataX

class FuelSiteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFuelSiteDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFuelSiteDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




    try {
        val fuelSite = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("fuelSite", DataX::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("fuelSite")
        }

        fuelSite?.let {
            binding.apply {
                tvNameDetail.text = if (it.name != null) {
                    it.name
                } else {
                    "No Value Found"
                }
                tvStateDetail.text = it.state?.let {
                    if (it.name != null) {
                        it.name
                    } else {
                        "No Value Found"
                    }
                }

                tvProvinceDetail.text = if (it.city != null) {
                    it.city
                } else {
                    "No Value Found"
                }

                tvTerminalDetail.text = if (it.terminal_name != null) {
                    it.terminal_name
                } else {
                    "No Value Found"
                }
            }
        }
    } catch (e: Exception){
        Toast.makeText(applicationContext,"Exception :" +e.message,Toast.LENGTH_LONG).show()
    }

    }
}