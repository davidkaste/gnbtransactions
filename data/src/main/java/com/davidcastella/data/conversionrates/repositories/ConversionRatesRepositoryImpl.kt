package com.davidcastella.data.conversionrates.repositories

import arrow.core.Either
import com.davidcastella.data.conversionrates.datasources.GNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.repositories.mappers.ConversionRateResponseModelMapper
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import com.davidcastella.domain.core.failure.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConversionRatesRepositoryImpl(
    private val datasource: GNBConversionRatesDatasource,
    private val conversionRateMapper: ConversionRateResponseModelMapper
): ConversionRatesRepository {
    override fun getConversionRates(): Flow<Either<Failure, List<ConversionRate>>> = flow {
        try {
            val response = datasource.getConversionRates()
            emit(Either.Right(response.map(conversionRateMapper)))
        } catch (e: Exception) {
            emit(Either.Left(Failure.GENERIC_FAILURE))
        }
    }
}