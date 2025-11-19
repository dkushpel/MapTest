package com.mapapp.domain.usecase

import com.mapapp.domain.model.RouteMarker
import com.mapapp.domain.repository.RouteRepository

class GetRouteMarkersUseCase(
    private val repository: RouteRepository
) {
    suspend operator fun invoke(): List<RouteMarker> {
        val origin = repository.getOrigin()
        val markers = repository.getMarkers()
        
        return buildList {
            origin?.let {
                add(RouteMarker(id = "origin", coordinate = it, isOrigin = true))
            }
            addAll(markers)
        }
    }
}

