package com.robin.mvlproject.ui.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
), OnMapReadyCallback, GoogleMap.OnCameraMoveListener {

    private val viewModel: HomeViewModel by viewModels()

    private var map: GoogleMap? = null

    override fun start() {
        binding.vm = viewModel
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {

                } else {
                    requireActivity().finish()
                }
            }



        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
                mapFragment?.getMapAsync(this@HomeFragment)
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }

    }

    override fun observe() {
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        val seoul = LatLng(37.56, 126.97)
        map.addMarker(MarkerOptions().position(seoul).title("서울"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 10F))
        map.setOnCameraMoveListener(this)
    }

    override fun onCameraMove() {
        Timber.d("CameraPosition = ${map?.cameraPosition}")
        val latitude = map?.cameraPosition?.target?.latitude
        val longitude = map?.cameraPosition?.target?.longitude
        if(latitude != null && longitude != null)
            viewModel.getAQI(latitude, longitude)
    }

    companion object {
        val instance = HomeFragment()
    }

}