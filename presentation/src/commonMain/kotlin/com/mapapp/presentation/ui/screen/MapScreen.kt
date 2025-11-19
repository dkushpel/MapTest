package com.mapapp.presentation.ui.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.mapapp.presentation.di.koinInject
import com.mapapp.presentation.ui.content.MapContent
import com.mapapp.presentation.ui.viewmodel.RouteViewModel

object MapScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: RouteViewModel = koinInject()
        MapContent(viewModel = viewModel)
    }
}

