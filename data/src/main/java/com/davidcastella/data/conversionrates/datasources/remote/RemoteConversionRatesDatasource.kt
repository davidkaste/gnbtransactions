package com.davidcastella.data.conversionrates.datasources.remote

import com.davidcastella.domain.conversionrates.entities.ConversionRate

interface RemoteConversionRatesDatasource {
    suspend fun getConversionRates(): List<ConversionRate>
}
