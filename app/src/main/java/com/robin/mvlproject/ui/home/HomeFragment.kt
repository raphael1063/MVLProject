package com.robin.mvlproject.ui.home

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
), OnMapReadyCallback {
    override fun start() {
        childFragmentManager.beginTransaction()
            .add(R.id.mapView, SupportMapFragment.newInstance())
            .commit()
    }

    override fun observe() {
    }

    companion object {
        val instance = HomeFragment()
    }

    override fun onMapReady(map: GoogleMap) {
    }
}