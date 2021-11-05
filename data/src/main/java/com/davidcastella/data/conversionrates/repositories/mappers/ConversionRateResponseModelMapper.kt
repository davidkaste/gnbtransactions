package com.davidcastella.data.conversionrates.repositories.mappers

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.gnb_api.models.ConversionRateResponseModel
import javax.inject.Inject

class ConversionRateResponseModelMapper @Inject constructor(): (ConversionRateResponseModel) -> ConversionRate {
    override fun invoke(model: ConversionRateResponseModel) = ConversionRate(
        model.from,
        model.to,
        model.rate.toBigDecimal()
    )
}