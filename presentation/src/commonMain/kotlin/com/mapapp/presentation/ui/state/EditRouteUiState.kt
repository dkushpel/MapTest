package com.mapapp.presentation.ui.state

data class EditRouteUiState(
    val latitude: String = "",
    val longitude: String = "",
    val error: String? = null,
    val isOrigin: Boolean = false
)

