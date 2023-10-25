package com.davidcastella.data.conversionrates.datasources.remote.mappers

import com.davidcastella.data.api.models.ConversionRateResponseModel
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import javax.inject.Inject

class ConversionRateResponseModelMapper @Inject constructor(): (ConversionRateResponseModel) -> ConversionRate {
    override fun invoke(model: ConversionRateResponseModel) = ConversionRate(
        model.from,
        model.to,
        model.rate.toBigDecimal()
    )
}
