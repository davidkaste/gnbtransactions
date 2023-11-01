package com.davidcastella.data.conversionrates.repositories

import com.davidcastella.data.conversionrates.datasources.remote.RemoteConversionRatesDatasource
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.Result
import java.io.IOException
import javax.inject.Inject

class ConversionRatesRepositoryImpl @Inject constructor(
    private val datasource: RemoteConversionRatesDatasource
): ConversionRatesRepository {
    override suspend fun getConversionRates(): Result<Failure, List<ConversionRate>> =
        try {
            val response = datasource.getConversionRates()
            println(response)
            Result.Success(response)
        } catch (ex: IOException) {
            Result.Failure(Failure.CONNECTION_FAILURE)
        } catch (ex: RuntimeException) {
            Result.Failure(Failure.HTTP_FAILURE)
        } catch (ex: Exception) {
            Result.Failure(Failure.GENERIC_FAILURE)
        }
    }
