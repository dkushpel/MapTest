package com.mapapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.mapapp.presentation.di.KoinInit
import com.mapapp.presentation.di.appModule
import com.mapapp.presentation.ui.screen.MapScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    KoinInit(modules = listOf(appModule)) {
        MaterialTheme {
            Navigator(MapScreen)
        }
    }
}