package com.mapapp.presentation.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mapapp.presentation.ui.screen.MapScreen
import com.mapapp.presentation.ui.viewmodel.EditRouteViewModel
import com.mapapp.presentation.ui.viewmodel.RouteViewModel

@Composable
fun EditRouteContent(
    editRouteViewModel: EditRouteViewModel,
    routeViewModel: RouteViewModel,
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.currentOrThrow
    val uiState by editRouteViewModel.uiState.collectAsState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top=45.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Edit Route",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = uiState.latitude,
                    onValueChange = editRouteViewModel::updateLatitude,
                    label = { Text("Latitude") },
                    placeholder = { Text("e.g., 1.35") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = uiState.error != null
                )
                
                OutlinedTextField(
                    value = uiState.longitude,
                    onValueChange = editRouteViewModel::updateLongitude,
                    label = { Text("Longitude") },
                    placeholder = { Text("e.g., 103.87") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = uiState.error != null
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = uiState.isOrigin,
                        onCheckedChange = editRouteViewModel::setIsOrigin
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Set as origin")
                }
                
                uiState.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val coordinate = editRouteViewModel.validateAndGetCoordinate()
                            if (coordinate != null) {
                                if (uiState.isOrigin) {
                                    routeViewModel.setOrigin(coordinate)
                                } else {
                                    routeViewModel.addMarker(coordinate)
                                }
                                editRouteViewModel.clearInputs()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (uiState.isOrigin) "Set Origin" else "Add Marker")
                    }
                    
                    TextButton(
                        onClick = {
                            editRouteViewModel.clearInputs()
                            navigator.pop()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Back to Map")
                    }
                }
            }
        }
    }
}

