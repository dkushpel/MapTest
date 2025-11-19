package com.mapapp.presentation.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapapp.presentation.ui.viewmodel.RouteViewModel

@Composable
expect fun MapContent(
    viewModel: RouteViewModel,
    modifier: Modifier = Modifier
)

