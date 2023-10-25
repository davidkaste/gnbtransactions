package com.davidcastella.data.conversionrates.datasources.remote

import com.davidcastella.data.api.BankService
import com.davidcastella.data.api.models.ConversionRateResponseModel
import com.davidcastella.data.conversionrates.datasources.remote.mappers.ConversionRateResponseModelMapper
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoteConversionRatesDatasourceImplTest {

    private lateinit var datasource: RemoteConversionRatesDatasourceImpl

    private val service: BankService = mockk()
    private val mapper: ConversionRateResponseModelMapper = mockk()

    private val conversionRate = ConversionRate("EUR", "USD", 12.3.toBigDecimal())

    @Before
    fun setUp() {
        coEvery { mapper.invoke(any()) } returns conversionRate

        datasource = RemoteConversionRatesDatasourceImpl(service, mapper)
    }

    @Test
    fun getConversionRates() = runBlocking {
        val expected = ConversionRateResponseModel("EUR", "USD", 1.23)
        ArrangeBuilder().withGetConversionRatesSuccess(listOf(expected))
        every { mapper(expected) } returns conversionRate

        val cr = datasource.getConversionRates()

        coVerify(exactly = 1) { service.getCurrencyRates() }

        val result = cr[0]
        assertEquals(conversionRate.from, result.from)
        assertEquals(conversionRate.to, result.to)
        assert(conversionRate == result)
    }

    inner class ArrangeBuilder {
        fun withGetConversionRatesSuccess(model: List<ConversionRateResponseModel>): ArrangeBuilder {
            coEvery { service.getCurrencyRates() } returns model
            return this
        }
    }
}
