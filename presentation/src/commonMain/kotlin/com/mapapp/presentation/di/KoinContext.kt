package com.mapapp.presentation.di

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module

private var koinInstance: Koin? = null

fun getKoinInstance(): Koin {
    return koinInstance ?: error("Koin has not been started. Call startKoinIfNeeded first.")
}

fun startKoinIfNeeded(modules: List<Module>): Koin {
    if (koinInstance == null) {
        val koinApplication = startKoin {
            modules(modules)
        }
        koinInstance = koinApplication.koin
    }
    return koinInstance!!
}

