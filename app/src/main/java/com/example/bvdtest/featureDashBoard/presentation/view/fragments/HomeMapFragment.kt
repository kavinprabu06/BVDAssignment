package com.example.bvdtest.featureDashBoard.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.bvdtest.R
import com.example.bvdtest.featureDashBoard.data.dataSources.remoteDataSource.model.DataX
import com.example.bvdtest.featureDashBoard.presentation.view.activities.FuelSiteDetailActivity
import com.example.bvdtest.featureDashBoard.presentation.viewModel.FuelSiteViewModel
import com.example.bvdtest.utils.common.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
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
            observeFuelSites()
            setupMarkerClickListener()
        }
    }

    private fun observeFuelSites() {
        val markerList = mutableListOf<Marker>()

        viewModel.allFuelSites.observe(viewLifecycleOwner, Observer { results ->
            when (results) {
                is Resource.Success -> {
                    results.data.data?.data?.forEach { fuelSite ->

                        val adjustedLatitude =
                            if (fuelSite.latitude_direction == "S") -fuelSite.latitude!! else fuelSite.latitude
                        val adjustedLongitude =
                            if (fuelSite.longitude_direction == "W") -fuelSite.longitude!! else fuelSite.longitude

                        if (adjustedLongitude !=null && adjustedLatitude !=null){

                            val latLng = LatLng(adjustedLatitude, adjustedLongitude)
                            val marker = googleMap.addMarker(
                                MarkerOptions().position(latLng).title(fuelSite.city)
                            )

                            if (marker != null) {
                                marker.tag = fuelSite
                                markerList.add(marker)

                            }
                        }
                    }

                    zoomToFitAllMarkers(markerList)
                    googleMap.uiSettings.isZoomControlsEnabled = true

                }

                is Resource.Failure -> {
                    Toast.makeText(
                        requireActivity(),
                        "API Failure :" + results.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is Resource.Loading -> {
                    Toast.makeText(requireActivity(), "Fetching....", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    private fun observeSelectedFuelSite() {
        viewModel.selectedFuelSite.observe(viewLifecycleOwner, Observer { fuelSite ->
            val intent = Intent(requireContext(), FuelSiteDetailActivity::class.java)
            intent.putExtra("fuelSite", fuelSite)
            startActivity(intent)
        })
    }

    private fun setupMarkerClickListener() {
        googleMap.setOnMarkerClickListener { marker ->
            val fuelSite = marker.tag as? DataX
            fuelSite?.let {
                viewModel.selectFuelSite(it)
            }
            val intent = Intent(requireContext(), FuelSiteDetailActivity::class.java)
            intent.putExtra("fuelSite", fuelSite)
            startActivity(intent);
            true
        }
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