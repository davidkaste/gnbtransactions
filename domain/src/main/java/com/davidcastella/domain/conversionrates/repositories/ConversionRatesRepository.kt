package com.davidcastella.domain.conversionrates.repositories

import arrow.core.Either
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.core.failure.Failure
import kotlinx.coroutines.flow.Flow

interface ConversionRatesRepository {
    fun getConversionRates(): Flow<Either<Failure, List<ConversionRate>>>
}