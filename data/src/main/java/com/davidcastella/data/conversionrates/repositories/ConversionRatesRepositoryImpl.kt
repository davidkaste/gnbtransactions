package com.davidcastella.data.conversionrates.repositories

import com.davidcastella.data.conversionrates.datasources.GNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.repositories.mappers.ConversionRateResponseModelMapper
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConversionRatesRepositoryImpl(
    private val datasource: GNBConversionRatesDatasource,
    private val conversionRateMapper: ConversionRateResponseModelMapper
): ConversionRatesRepository {
    override fun getConversionRates(): Flow<List<ConversionRate>> = flow {
        val response = datasource.getConversionRates()
        emit(response.map(conversionRateMapper))
    }
}