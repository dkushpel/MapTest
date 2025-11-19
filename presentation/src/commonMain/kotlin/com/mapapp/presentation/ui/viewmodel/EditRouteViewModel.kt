package com.mapapp.presentation.ui.viewmodel

import com.mapapp.domain.model.Coordinate
import com.mapapp.presentation.ui.state.EditRouteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditRouteViewModel {
    private val _uiState = MutableStateFlow(EditRouteUiState())
    val uiState: StateFlow<EditRouteUiState> = _uiState.asStateFlow()
    
    fun updateLatitude(latitude: String) {
        _uiState.value = _uiState.value.copy(
            latitude = latitude,
            error = null
        )
    }
    
    fun updateLongitude(longitude: String) {
        _uiState.value = _uiState.value.copy(
            longitude = longitude,
            error = null
        )
    }
    
    fun setIsOrigin(isOrigin: Boolean) {
        _uiState.value = _uiState.value.copy(isOrigin = isOrigin)
    }
    
    fun validateAndGetCoordinate(): Coordinate? {
        val state = _uiState.value
        val latStr = state.latitude.trim()
        val lngStr = state.longitude.trim()
        
        if (latStr.isEmpty()) {
            _uiState.value = state.copy(error = "Latitude cannot be empty")
            return null
        }
        
        if (lngStr.isEmpty()) {
            _uiState.value = state.copy(error = "Longitude cannot be empty")
            return null
        }
        
        val lat = latStr.toDoubleOrNull()
        val lng = lngStr.toDoubleOrNull()
        
        return when {
            lat == null -> {
                _uiState.value = state.copy(error = "Invalid latitude format")
                null
            }
            lng == null -> {
                _uiState.value = state.copy(error = "Invalid longitude format")
                null
            }
            else -> {
                val coordinate = Coordinate(lat, lng)
                if (coordinate.isValid()) {
                    coordinate
                } else {
                    _uiState.value = state.copy(
                        error = "Coordinates out of range. Latitude: -90 to 90, Longitude: -180 to 180"
                    )
                    null
                }
            }
        }
    }
    
    fun clearInputs() {
        _uiState.value = EditRouteUiState(isOrigin = _uiState.value.isOrigin)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

