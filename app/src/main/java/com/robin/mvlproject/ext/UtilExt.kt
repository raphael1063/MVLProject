package com.robin.mvlproject.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import kotlin.math.floor

@SuppressLint("MissingPermission")
fun Activity.getCurrentLocation() : Location? {
    val locationManager =
        this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val providers = locationManager.getProviders(true)
    var bestLocation: Location? = null
    for(provider in providers) {
        val l = locationManager.getLastKnownLocation(provider) ?: continue
        if(bestLocation == null || l.accuracy < bestLocation.accuracy) {
            bestLocation = l
        }
    }
    return bestLocation
}

fun Double.getFloor3() = floor(this*1000) /1000