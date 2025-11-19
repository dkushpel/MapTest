package com.mapapp.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.module.Module

@Composable
fun KoinInit(
    modules: List<Module>,
    content: @Composable () -> Unit
) {

    remember(modules) {
        startKoinIfNeeded(modules)
    }
    
    content()
}

