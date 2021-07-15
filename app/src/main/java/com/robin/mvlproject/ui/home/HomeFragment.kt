package com.robin.mvlproject.ui.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.databinding.FragmentHomeBinding
import timber.log.Timber

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
), OnMapReadyCallback, GoogleMap.OnCameraMoveListener {

    private var map: GoogleMap? = null

    override fun start() {
        Timber.d("start!!!!!!!!!!!")
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

    companion object {
        val instance = HomeFragment()
    }

    override fun onCameraMove() {
        Timber.d("CameraPosition = ${map?.cameraPosition}")
//        binding.tvHomeLabelA.text = "lat = ${map?.cameraPosition?.target?.latitude} \n lng = ${map?.cameraPosition?.target?.longitude}"
    }
}