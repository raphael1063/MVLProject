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
import com.robin.mvlproject.data.entities.Step.*
import com.robin.mvlproject.databinding.FragmentHomeBinding
import com.robin.mvlproject.ext.getCurrentLocation
import com.robin.mvlproject.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
), OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveListener {

    private val viewModel: HomeViewModel by viewModels()

    private val sharedViewModel: MainViewModel by activityViewModels()

    private var map: GoogleMap? = null

    override fun start() {
        binding.vm = viewModel

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    setCurrentLocation()
                } else {
                    requireActivity().finish()
                }
            }

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                //현재 위치로 설정
                setCurrentLocation()
            }
            else -> {
                //권한 요청
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

    }

    override fun observe() {
        with(viewModel) {
            //지도에서 마커 제거
            clearMap.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let {
                    map?.clear()
                }
            })
            //카메라 이동
            moveCamera.observe(viewLifecycleOwner, { latLng ->
                map?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(latLng[0], latLng[1]),
                        13F
                    )
                )
            })
            labelA.observe(viewLifecycleOwner, {
                map?.addMarker(
                    MarkerOptions()
                        .position(LatLng(it.latitude, it.longitude))
                        .title("A")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                )
            })
            labelB.observe(viewLifecycleOwner, {
                map?.addMarker(
                    MarkerOptions()
                        .position(LatLng(it.latitude, it.longitude))
                        .title("B")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                )
            })
            //A 레이블 클릭
            actionLabelAClicked.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let { label ->
                    sharedViewModel.openDetail(label)
                }
            })
            //B 레이블 클릭
            actionLabelBClicked.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let { label ->
                    sharedViewModel.openDetail(label)
                }
            })
            actionEmptyLabelClicked.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let {
                    sharedViewModel.openLabelLog(it)
                }
            })
            //Book 버튼 클릭
            actionBookClicked.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let { list ->
                    sharedViewModel.openPrice(list)
                }
            })
        }
        with(sharedViewModel) {
            //레이블 업데이트
            updateLabel.observe(viewLifecycleOwner, { label ->
                viewModel.updateLabel(label)
            })
            moveToPosition.observe(viewLifecycleOwner, { book ->
                viewModel.moveToPosition(book)
            })
        }
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map.apply {
            setOnCameraIdleListener(this@HomeFragment)
            setOnCameraMoveListener(this@HomeFragment)
        }
        viewModel.moveCamera()
    }

    override fun onCameraMove() {
        viewModel.onCameraMove()
    }

    override fun onCameraIdle() {
        viewModel.onCameraIdle(
            map?.cameraPosition?.target?.latitude ?: 0.0,
            map?.cameraPosition?.target?.longitude ?: 0.0
        )
    }

    private fun setCurrentLocation() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this@HomeFragment)
        viewModel.init(
            requireActivity().getCurrentLocation()?.latitude ?: 0.0,
            requireActivity().getCurrentLocation()?.longitude ?: 0.0
        )
    }

    companion object {
        fun getInstance() = HomeFragment()
    }
}