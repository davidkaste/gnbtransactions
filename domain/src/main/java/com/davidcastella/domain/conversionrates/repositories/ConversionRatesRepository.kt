package com.davidcastella.domain.conversionrates.repositories

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import kotlinx.coroutines.flow.Flow

interface ConversionRatesRepository {
    fun getConversionRates(): Flow<List<ConversionRate>>
}