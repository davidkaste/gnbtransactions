package com.davidcastella.data.conversionrates.repositories

import com.davidcastella.data.conversionrates.datasources.GNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.repositories.mappers.ConversionRateResponseModelMapper
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.gnb_api.models.ConversionRateResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ConversionRatesRepositoryImplTest {

    private lateinit var repository: ConversionRatesRepositoryImpl

    private val datasource: GNBConversionRatesDatasource = mockk()
    private val mapper: ConversionRateResponseModelMapper = mockk()

    private val conversionRateModel = ConversionRateResponseModel("EUR", "USD", 12.3)
    private val conversionRate = ConversionRate("EUR", "USD", 12.3.toBigDecimal())

    @Before
    fun setUp() {
        coEvery { datasource.getConversionRates() } returns listOf(conversionRateModel)
        coEvery { mapper.invoke(any()) } returns conversionRate

        repository = ConversionRatesRepositoryImpl(datasource, mapper)
    }

    @Test
    fun getConversionRates() = runBlocking {
        val result = repository.getConversionRates()

        result.collect {
            Assert.assertEquals(conversionRate, it.first())
        }

        coVerify(exactly = 1) { datasource.getConversionRates() }
        coVerify(exactly = 1) { mapper.invoke(any()) }
    }
}