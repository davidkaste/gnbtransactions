package com.davidcastella.domain.conversionrates.interactors

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import com.davidcastella.domain.core.FlowUseCase
import com.davidcastella.domain.core.NoParams
import kotlinx.coroutines.flow.Flow

class GetConversionRates(
    private val repository: ConversionRatesRepository
) : FlowUseCase<NoParams, List<ConversionRate>> {
    override fun invoke(p: NoParams): Flow<List<ConversionRate>> =
        repository.getConversionRates()
}