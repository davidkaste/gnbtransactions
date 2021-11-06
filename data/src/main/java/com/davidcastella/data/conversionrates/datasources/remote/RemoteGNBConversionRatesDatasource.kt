package com.davidcastella.data.conversionrates.datasources.remote

import com.davidcastella.data.api.BankService
import com.davidcastella.data.api.models.ConversionRateResponseModel
import com.davidcastella.data.conversionrates.datasources.GNBConversionRatesDatasource
import javax.inject.Inject

class RemoteGNBConversionRatesDatasource @Inject constructor(
    private val service: BankService
) : GNBConversionRatesDatasource {

    override suspend fun getConversionRates(): List<ConversionRateResponseModel> =
        service.getCurrencyRates()
}