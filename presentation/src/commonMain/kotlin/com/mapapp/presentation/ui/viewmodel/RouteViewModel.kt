package com.mapapp.presentation.ui.viewmodel

import com.mapapp.domain.model.Coordinate
import com.mapapp.domain.usecase.AddMarkerUseCase
import com.mapapp.domain.usecase.GetRouteMarkersUseCase
import com.mapapp.domain.usecase.SetOriginUseCase
import com.mapapp.presentation.ui.state.RouteUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

expect class ViewModelScope() {
    val scope: CoroutineScope
}

class RouteViewModel(
    private val addMarkerUseCase: AddMarkerUseCase,
    private val setOriginUseCase: SetOriginUseCase,
    private val getRouteMarkersUseCase: GetRouteMarkersUseCase
) {
    private val viewModelScope = ViewModelScope().scope
    
    private val _uiState = MutableStateFlow(RouteUiState())
    val uiState: StateFlow<RouteUiState> = _uiState.asStateFlow()
    
    init {
        loadMarkers()
    }
    
    fun addMarker(coordinate: Coordinate) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            addMarkerUseCase(coordinate)
                .onSuccess {
                    loadMarkers()
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to add marker"
                    )
                }
        }
    }
    
    fun setOrigin(coordinate: Coordinate) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            setOriginUseCase(coordinate)
                .onSuccess {
                    loadMarkers()
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to set origin"
                    )
                }
        }
    }
    
    private fun loadMarkers() {
        viewModelScope.launch {
            try {
                val markers = getRouteMarkersUseCase()
                _uiState.value = _uiState.value.copy(
                    markers = markers,
                    isLoading = false,
                    error = null
                )
            } catch (error: Throwable) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to load markers"
                )
            }
        }
    }

}

