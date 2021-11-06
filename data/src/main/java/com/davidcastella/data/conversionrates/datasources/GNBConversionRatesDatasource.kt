package com.davidcastella.data.conversionrates.datasources

import com.davidcastella.data.api.models.ConversionRateResponseModel

interface GNBConversionRatesDatasource {
    suspend fun getConversionRates(): List<ConversionRateResponseModel>
}