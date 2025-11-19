package com.mapapp.presentation.ui.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

actual class ViewModelScope {
    actual val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}

