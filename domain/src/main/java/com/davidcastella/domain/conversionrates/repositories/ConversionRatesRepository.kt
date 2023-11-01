package com.davidcastella.domain.conversionrates.repositories

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.Result

interface ConversionRatesRepository {
    suspend fun getConversionRates(): Result<Failure, List<ConversionRate>>
}