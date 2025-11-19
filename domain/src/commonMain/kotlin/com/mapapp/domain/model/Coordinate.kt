package com.mapapp.domain.model

data class Coordinate(
    val latitude: Double,
    val longitude: Double
) {
    fun isValid(): Boolean {
        return latitude in -90.0..90.0 && longitude in -180.0..180.0
    }
}

