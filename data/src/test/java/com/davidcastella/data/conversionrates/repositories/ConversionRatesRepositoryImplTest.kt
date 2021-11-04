package com.davidcastella.data.conversionrates.repositories

import arrow.core.Either
import com.davidcastella.data.conversionrates.datasources.GNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.repositories.mappers.ConversionRateResponseModelMapper
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.gnb_api.models.ConversionRateResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class ConversionRatesRepositoryImplTest {

    private lateinit var repository: ConversionRatesRepositoryImpl

    private val datasource: GNBConversionRatesDatasource = mockk()
    private val mapper: ConversionRateResponseModelMapper = mockk()

    private val conversionRateModel = ConversionRateResponseModel("EUR", "USD", 12.3)
    private val conversionRate = ConversionRate("EUR", "USD", 12.3.toBigDecimal())
    private val genericFailure = Failure.GENERIC_FAILURE

    @Before
    fun setUp() {
        coEvery { mapper.invoke(any()) } returns conversionRate

        repository = ConversionRatesRepositoryImpl(datasource, mapper)
    }

    @Test
    fun `given repository when call getConversionRates method succeeds then return correct data`() = runBlocking {
        coEvery { datasource.getConversionRates() } returns listOf(conversionRateModel)

        val result = repository.getConversionRates()

        result.collect {
            Assert.assertEquals(conversionRate, (it as Either.Right).value.first())
        }

        coVerify(exactly = 1) { datasource.getConversionRates() }
        coVerify(exactly = 1) { mapper.invoke(any()) }
    }

    @Test
    fun `given repository when call getConversionRates method fails then return correct failure`() = runBlocking {
        coEvery { datasource.getConversionRates() } throws Exception()
        val result = repository.getConversionRates()

        result.collect {
            Assert.assertEquals(genericFailure, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getConversionRates() }
        coVerify(exactly = 0) { mapper.invoke(any()) }
    }
}