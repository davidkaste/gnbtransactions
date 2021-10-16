package com.davidcastella.data.conversionrates.repositories.mappers

import com.davidcastella.data.transactions.repositories.mappers.TransactionResponseModelMapper
import com.davidcastella.gnb_api.models.ConversionRateResponseModel
import com.davidcastella.gnb_api.models.TransactionResponseModel
import org.junit.Assert.*

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