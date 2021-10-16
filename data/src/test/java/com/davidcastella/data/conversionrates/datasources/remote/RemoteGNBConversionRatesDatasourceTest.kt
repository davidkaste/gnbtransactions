package com.davidcastella.data.conversionrates.datasources.remote

import com.davidcastella.gnb_api.BankServiceAPI
import com.davidcastella.gnb_api.models.ConversionRateResponseModel
import com.davidcastella.gnb_api.service.GNBankService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class RemoteGNBConversionRatesDatasourceTest {

    private lateinit var datasource: RemoteGNBConversionRatesDatasource

    private val service: BankServiceAPI = mockk()
    private val retrofit: Retrofit = mockk()
    private val api: GNBankService = mockk()

    @Before
    fun setUp() {
        coEvery { service.api } returns retrofit
        coEvery { retrofit.create(GNBankService::class.java) } returns api
        datasource = RemoteGNBConversionRatesDatasource(service)
    }

    @Test
    fun getConversionRates() = runBlocking {
        val expected = ConversionRateResponseModel("EUR", "USD", 1.23)
        ArrangeBuilder().withGetConversionRatesSuccess(listOf(expected))

        val cr = datasource.getConversionRates()

        coVerify(exactly = 1) { service.api }

        val result = cr[0]
        assertEquals(expected.from, result.from)
        assertEquals(expected.to, result.to)
        assertEquals(expected.rate, result.rate, 0.0)
    }

    inner class ArrangeBuilder {
        fun withGetConversionRatesSuccess(model: List<ConversionRateResponseModel>): ArrangeBuilder {
            coEvery { api.getCurrencyRates() } returns model
            return this
        }
    }
}