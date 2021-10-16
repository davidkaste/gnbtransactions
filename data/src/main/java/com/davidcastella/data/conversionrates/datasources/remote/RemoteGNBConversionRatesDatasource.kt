package com.davidcastella.data.conversionrates.datasources.remote

import com.davidcastella.data.conversionrates.datasources.GNBConversionRatesDatasource
import com.davidcastella.gnb_api.BankServiceAPI
import com.davidcastella.gnb_api.models.ConversionRateResponseModel
import com.davidcastella.gnb_api.service.GNBankService

class RemoteGNBConversionRatesDatasource(
    serviceApi: BankServiceAPI
): GNBConversionRatesDatasource {

    private val service = serviceApi.api.create(GNBankService::class.java)

    override suspend fun getConversionRates(): List<ConversionRateResponseModel> = service.getCurrencyRates()
}