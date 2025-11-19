package com.mapapp.presentation.ui.state

import com.mapapp.domain.model.RouteMarker

data class RouteUiState(
    val markers: List<RouteMarker> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

