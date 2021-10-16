package com.davidcastella.data.conversionrates.datasources

import com.davidcastella.gnb_api.models.ConversionRateResponseModel

interface GNBConversionRatesDatasource {
    suspend fun getConversionRates(): List<ConversionRateResponseModel>
}