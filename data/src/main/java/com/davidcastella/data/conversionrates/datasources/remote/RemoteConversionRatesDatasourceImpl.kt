package com.davidcastella.data.conversionrates.datasources.remote

import com.davidcastella.data.api.BankService
import com.davidcastella.data.conversionrates.datasources.remote.mappers.ConversionRateResponseModelMapper
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import javax.inject.Inject

class RemoteConversionRatesDatasourceImpl @Inject constructor(
    private val service: BankService,
    private val mapper: ConversionRateResponseModelMapper
) : RemoteConversionRatesDatasource {

    override suspend fun getConversionRates(): List<ConversionRate> =
        service.getCurrencyRates().map(mapper)
}
