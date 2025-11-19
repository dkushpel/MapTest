package com.mapapp.presentation.di

inline fun <reified T : Any> koinInject(): T {
    val koin = getKoinInstance()
    return koin.get<T>()
}

