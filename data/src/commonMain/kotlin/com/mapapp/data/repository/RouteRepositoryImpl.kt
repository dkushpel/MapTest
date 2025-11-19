package com.mapapp.data.repository

import com.mapapp.domain.model.Coordinate
import com.mapapp.domain.model.RouteMarker
import com.mapapp.domain.repository.RouteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random

class RouteRepositoryImpl : RouteRepository {
    // StateFlow is already thread-safe for read/write operations
    private val _origin = MutableStateFlow<Coordinate?>(null)
    private val _markers = MutableStateFlow<List<RouteMarker>>(emptyList())
    
    override suspend fun getOrigin(): Coordinate? {
        return _origin.value
    }
    
    override suspend fun setOrigin(coordinate: Coordinate) {
        _origin.value = coordinate
    }
    
    override suspend fun getMarkers(): List<RouteMarker> {
        return _markers.value
    }
    
    override suspend fun addMarker(coordinate: Coordinate): RouteMarker {
        val marker = RouteMarker(
            id = Random.nextLong().toString(),
            coordinate = coordinate,
            isOrigin = false
        )
        _markers.value = _markers.value + marker
        return marker
    }
    
    override suspend fun removeMarker(markerId: String) {
        _markers.value = _markers.value.filter { it.id != markerId }
    }
    
    override suspend fun clearAllMarkers() {
        _markers.value = emptyList()
    }
}

