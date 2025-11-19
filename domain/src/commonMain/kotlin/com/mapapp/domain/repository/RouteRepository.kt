package com.mapapp.domain.repository

import com.mapapp.domain.model.Coordinate
import com.mapapp.domain.model.RouteMarker

interface RouteRepository {
    suspend fun getOrigin(): Coordinate?
    suspend fun setOrigin(coordinate: Coordinate)
    suspend fun getMarkers(): List<RouteMarker>
    suspend fun addMarker(coordinate: Coordinate): RouteMarker
    suspend fun removeMarker(markerId: String)
    suspend fun clearAllMarkers()
}

