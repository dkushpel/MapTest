package com.mapapp.presentation.di

import com.mapapp.data.repository.RouteRepositoryImpl
import com.mapapp.domain.repository.RouteRepository
import com.mapapp.domain.usecase.AddMarkerUseCase
import com.mapapp.domain.usecase.GetRouteMarkersUseCase
import com.mapapp.domain.usecase.SetOriginUseCase
import com.mapapp.presentation.ui.viewmodel.EditRouteViewModel
import com.mapapp.presentation.ui.viewmodel.RouteViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::RouteRepositoryImpl) bind RouteRepository::class
    
    factoryOf(::AddMarkerUseCase)
    factoryOf(::SetOriginUseCase)
    factoryOf(::GetRouteMarkersUseCase)
    
    singleOf(::RouteViewModel)
    factoryOf(::EditRouteViewModel)
}

