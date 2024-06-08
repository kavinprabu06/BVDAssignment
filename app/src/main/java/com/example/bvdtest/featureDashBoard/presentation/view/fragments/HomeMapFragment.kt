package com.example.bvdtest.featureDashBoard.presentation.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.bvdtest.featureDashBoard.presentation.viewModel.FuelSiteViewModel
import com.example.bvdtest.R
import com.example.bvdtest.utils.common.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMapFragment : Fragment() {

    private lateinit var googleMap: GoogleMap
    private var mapFragment: SupportMapFragment? = null

    private val viewModel: FuelSiteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel.getAllFuelSites()
        mapFragment =
            childFragmentManager.findFragmentByTag("mapFragment") as SupportMapFragment


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment?.getMapAsync { map ->
            googleMap = map
            setupMap()
        }

    }

    private fun setupMap() {
        val markerList = mutableListOf<Marker>()

        viewModel.allFuelSites.observe(viewLifecycleOwner, Observer { results ->
            when (results) {
                is Resource.Success -> {
                    Log.d("MapFragmentApiCall", "" + results.data)
                    results.data.data.data.forEach { fuelSite ->

                        val adjustedLatitude = if (fuelSite.latitude_direction == "S") -fuelSite.latitude else fuelSite.latitude
                        val adjustedLongitude = if (fuelSite.longitude_direction == "W") -fuelSite.longitude else fuelSite.longitude

                        val latLng = LatLng(adjustedLatitude, adjustedLongitude)
                        val marker = googleMap.addMarker(MarkerOptions().position(latLng).title(fuelSite.city))
                        if (marker != null) {
                            markerList.add(marker)
                        }
                    }
//                    val cameraPosition = CameraPosition.Builder()
//                        .target(LatLng(defaultLatitude, defaultLongitude)) // Set default position
//                        .zoom(12f) // Set zoom level (0-21)
//                        .build()
//                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

                    zoomToFitAllMarkers(markerList)
                    // Enable zoom controls
                    googleMap.uiSettings.isZoomControlsEnabled = true

                }

                is Resource.Failure -> {
                    Log.d("MapFragmentApiCall", "" + results.message)
                }

                is Resource.Loading -> {

                }

            }
        })
    }

    private fun zoomToFitAllMarkers(markers: List<Marker>) {
        if (markers.isEmpty()) return

        val builder = LatLngBounds.Builder()
        for (marker in markers) {
            builder.include(marker.position)
        }

        val bounds = builder.build()
        val padding = 100 // Padding in pixels from the map's edge

        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.animateCamera(cu)
    }

}