package com.robin.mvlproject.ui.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.data.entities.MarkerButtonState.*
import com.robin.mvlproject.data.entities.Step
import com.robin.mvlproject.data.entities.Step.*
import com.robin.mvlproject.databinding.FragmentHomeBinding
import com.robin.mvlproject.ext.getCurrentLocation
import com.robin.mvlproject.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
), OnMapReadyCallback, GoogleMap.OnCameraMoveListener {

    private val viewModel: HomeViewModel by viewModels()

    private val sharedViewModel: MainViewModel by activityViewModels()

    private var map: GoogleMap? = null

    private var currentLatitude: Double = 0.0

    private var currentLongitude: Double = 0.0

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
                currentLatitude = requireActivity().getCurrentLocation()?.latitude ?: 0.0
                currentLongitude = requireActivity().getCurrentLocation()?.longitude ?: 0.0
                viewModel.init(currentLatitude, currentLongitude)
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
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
        with(viewModel) {
            markerState.observe(this@HomeFragment, { state ->
                when (state) {
                    A_SELECTED -> {
                        map?.addMarker(
                            MarkerOptions()
                                .position(LatLng(currentLatitude, currentLongitude))
                                .title("A")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                        )
                    }
                    B_SELECTED -> {
                        map?.addMarker(
                            MarkerOptions()
                                .position(LatLng(currentLatitude, currentLongitude))
                                .title("B")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        )
                    }
                    else -> {
                        //do nothing
                    }
                }
            })
            actionLabelAClicked.observe(this@HomeFragment, { event ->
                event.getContentIfNotHandled()?.let { label ->
                    sharedViewModel.openDetail(label)
                }
            })
            actionLabelBClicked.observe(this@HomeFragment, { event ->
                event.getContentIfNotHandled()?.let { label ->
                    sharedViewModel.openDetail(label)
                }
            })
            actionBookClicked.observe(this@HomeFragment, { event ->
                event.getContentIfNotHandled()?.let { model ->
                    sharedViewModel.openPrice(model)
                }
            })
        }
        with(sharedViewModel) {
            updateLabel.observe(this@HomeFragment, { label ->
                viewModel.updateLabel(label)
            })
        }
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        val currentLocation = LatLng(currentLatitude, currentLongitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))
        map.setOnCameraMoveListener(this)
    }

    override fun onCameraMove() {
        Timber.d("CameraPosition = ${map?.cameraPosition}")
        currentLatitude = map?.cameraPosition?.target?.latitude ?: 0.0
        currentLongitude = map?.cameraPosition?.target?.longitude ?: 0.0
        viewModel.onCameraMoved(currentLatitude, currentLongitude)
    }

    companion object {
        val instance = HomeFragment()
    }

}