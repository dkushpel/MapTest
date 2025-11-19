package com.mapapp.domain.model

data class RouteMarker(
    val id: String,
    val coordinate: Coordinate,
    val isOrigin: Boolean = false
)

