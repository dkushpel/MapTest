package com.mapapp.presentation.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.mapapp.presentation.ui.screen.EditRouteScreen
import com.mapapp.presentation.ui.viewmodel.RouteViewModel
@Composable
actual fun MapContent(
    viewModel: RouteViewModel,
    modifier: Modifier
) {
    val navigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()
    
    val defaultLocation = remember { LatLng(46.4775, 30.7326) }
    val originMarker = remember(uiState.markers) { 
        uiState.markers.firstOrNull { it.isOrigin } 
    }
    val centerLocation = remember(originMarker) {
        originMarker?.let { 
            LatLng(it.coordinate.latitude, it.coordinate.longitude) 
        } ?: defaultLocation
    }
    
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerLocation, 12f)
    }
    
    // Update camera when center location coordinates change
    LaunchedEffect(centerLocation.latitude, centerLocation.longitude) {
        val newCameraPosition = CameraPosition.fromLatLngZoom(centerLocation, 12f)
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(newCameraPosition)
        cameraPositionState.animate(cameraUpdate)
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            uiState.markers.forEach { marker ->
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            marker.coordinate.latitude,
                            marker.coordinate.longitude
                        )
                    ),
                    title = if (marker.isOrigin) "Origin" else "Marker",
                    snippet = "${marker.coordinate.latitude}, ${marker.coordinate.longitude}"
                )
            }
        }
        
        FloatingActionButton(
            onClick = { navigator.push(EditRouteScreen) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

