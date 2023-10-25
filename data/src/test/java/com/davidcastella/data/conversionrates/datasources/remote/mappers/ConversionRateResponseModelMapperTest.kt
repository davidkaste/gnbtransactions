package com.davidcastella.data.conversionrates.datasources.remote.mappers

import com.davidcastella.data.api.models.ConversionRateResponseModel
import org.junit.Assert.assertEquals
import org.junit.Test

class ConversionRateResponseModelMapperTest {

    private val mapper = ConversionRateResponseModelMapper()

    private val conversionRateResponseModel = ConversionRateResponseModel("EUR", "USD", 2.0)

    @Test
    operator fun invoke() {
        val result = mapper(conversionRateResponseModel)

        assertEquals(conversionRateResponseModel.from, result.from)
        assertEquals(conversionRateResponseModel.to, result.to)
        assertEquals(conversionRateResponseModel.rate.toBigDecimal(), result.rate)
    }
}
