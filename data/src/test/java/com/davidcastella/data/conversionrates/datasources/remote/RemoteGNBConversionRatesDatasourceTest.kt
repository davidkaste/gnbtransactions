package com.davidcastella.data.conversionrates.datasources.remote

import com.davidcastella.data.api.BankService
import com.davidcastella.data.api.models.ConversionRateResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoteGNBConversionRatesDatasourceTest {

    private lateinit var datasource: RemoteGNBConversionRatesDatasource

    private val service: BankService = mockk()

    @Before
    fun setUp() {
        datasource = RemoteGNBConversionRatesDatasource(service)
    }

    @Test
    fun getConversionRates() = runBlocking {
        val expected = ConversionRateResponseModel("EUR", "USD", 1.23)
        ArrangeBuilder().withGetConversionRatesSuccess(listOf(expected))

        val cr = datasource.getConversionRates()

        coVerify(exactly = 1) { service.getCurrencyRates() }

        val result = cr[0]
        assertEquals(expected.from, result.from)
        assertEquals(expected.to, result.to)
        assertEquals(expected.rate, result.rate, 0.0)
    }

    inner class ArrangeBuilder {
        fun withGetConversionRatesSuccess(model: List<ConversionRateResponseModel>): ArrangeBuilder {
            coEvery { service.getCurrencyRates() } returns model
            return this
        }
    }
}