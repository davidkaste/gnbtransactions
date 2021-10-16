package com.davidcastella.domain.conversionrates.interactors

import com.davidcastella.domain.FlowUseCase
import com.davidcastella.domain.NoParams
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import kotlinx.coroutines.flow.Flow

class GetConversionRates(
    private val repository: ConversionRatesRepository
): FlowUseCase<NoParams, List<ConversionRate>> {
    override fun invoke(p: NoParams): Flow<List<ConversionRate>> =
        repository.getConversionRates()
}