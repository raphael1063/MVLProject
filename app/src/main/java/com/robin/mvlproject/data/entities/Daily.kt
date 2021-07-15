package com.robin.mvlproject.data.entities

data class Daily(
    val o3: List<O3>,
    val pm10: List<Pm10>,
    val pm25: List<Pm25>,
    val uvi: List<Uvi>
)