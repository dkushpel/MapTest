package com.mapapp.presentation.ui.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.mapapp.presentation.di.koinInject
import com.mapapp.presentation.ui.content.EditRouteContent
import com.mapapp.presentation.ui.viewmodel.EditRouteViewModel
import com.mapapp.presentation.ui.viewmodel.RouteViewModel

object EditRouteScreen : Screen {
    @Composable
    override fun Content() {
        val editRouteViewModel: EditRouteViewModel = koinInject()
        val routeViewModel: RouteViewModel = koinInject()
        EditRouteContent(
            editRouteViewModel = editRouteViewModel,
            routeViewModel = routeViewModel
        )
    }
}

