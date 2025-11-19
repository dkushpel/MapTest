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
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cocoapods.GoogleMaps.GMSMarker
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2DMake
import com.mapapp.presentation.ui.screen.EditRouteScreen
import com.mapapp.presentation.ui.viewmodel.RouteViewModel
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapContent(
    viewModel: RouteViewModel,
    modifier: Modifier
) {
    val navigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()
    
    val mapView: GMSMapView = remember { 
        GMSMapView()
    }
    
    val defaultLat = remember { 46.4775 }
    val defaultLng = remember { 30.7326 }
    val originMarker = remember(uiState.markers) { 
        uiState.markers.firstOrNull { it.isOrigin } 
    }
    val centerLat = remember(originMarker, defaultLat) {
        originMarker?.coordinate?.latitude ?: defaultLat
    }
    val centerLng = remember(originMarker, defaultLng) {
        originMarker?.coordinate?.longitude ?: defaultLng
    }
    
    // Update camera position
    LaunchedEffect(centerLat, centerLng) {
        val cameraPosition = GMSCameraPosition.cameraWithLatitude(
            latitude = centerLat,
            longitude = centerLng,
            zoom = 12.0f
        )
        val cameraUpdate = GMSCameraUpdate.setCamera(cameraPosition)
        mapView.moveCamera(cameraUpdate)
    }
    
    // Update markers when state changes
    LaunchedEffect(uiState.markers) {
        mapView.clear()
        uiState.markers.forEach { marker ->
            val gmsMarker = GMSMarker()
            gmsMarker.position = CLLocationCoordinate2DMake(
                marker.coordinate.latitude,
                marker.coordinate.longitude
            )
            gmsMarker.title = if (marker.isOrigin) "Origin" else "Marker"
            gmsMarker.snippet = "${marker.coordinate.latitude}, ${marker.coordinate.longitude}"
            gmsMarker.map = mapView
        }
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        UIKitView(
            modifier = Modifier.fillMaxSize(),
            factory = { mapView }
        )
        
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

