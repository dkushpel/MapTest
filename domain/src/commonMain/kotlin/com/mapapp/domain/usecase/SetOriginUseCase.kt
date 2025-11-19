package com.mapapp.domain.usecase

import com.mapapp.domain.model.Coordinate
import com.mapapp.domain.repository.RouteRepository

class SetOriginUseCase(
    private val repository: RouteRepository
) {
    suspend operator fun invoke(coordinate: Coordinate): Result<Unit> {
        return if (coordinate.isValid()) {
            repository.setOrigin(coordinate)
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Invalid coordinates"))
        }
    }
}

