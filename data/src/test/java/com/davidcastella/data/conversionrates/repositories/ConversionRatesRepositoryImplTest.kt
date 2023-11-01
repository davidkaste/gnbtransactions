package com.davidcastella.data.conversionrates.repositories

import com.davidcastella.data.conversionrates.datasources.remote.RemoteConversionRatesDatasource
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class ConversionRatesRepositoryImplTest {

    private lateinit var repository: ConversionRatesRepositoryImpl

    private val datasource: RemoteConversionRatesDatasource = mockk()

    private val conversionRate = ConversionRate("EUR", "USD", 12.3.toBigDecimal())

    @Before
    fun setUp() {
        repository = ConversionRatesRepositoryImpl(datasource)
    }

    @Test
    fun `given repository when call getConversionRates method succeeds then return correct data`() =
        runBlocking {
            coEvery { datasource.getConversionRates() } returns listOf(conversionRate)

            val result = repository.getConversionRates()

            Assert.assertEquals(conversionRate, (result as Result.Success).value.first())
            coVerify(exactly = 1) { datasource.getConversionRates() }
        }

    @Test
    fun `given repository when call getConversionRates method fails then return correct generic failure`() =
        runBlocking {
            coEvery { datasource.getConversionRates() } throws Exception()
            val result = repository.getConversionRates()

            Assert.assertEquals(Failure.GENERIC_FAILURE, (result as Result.Failure).failure)
            coVerify(exactly = 1) { datasource.getConversionRates() }
        }

    @Test
    fun `given repository when call getConversionRates method fails then return correct connection failure`() =
        runBlocking {
            coEvery { datasource.getConversionRates() } throws UnknownHostException()
            val result = repository.getConversionRates()

            Assert.assertEquals(Failure.CONNECTION_FAILURE, (result as Result.Failure).failure)
            coVerify(exactly = 1) { datasource.getConversionRates() }
        }

    @Test
    fun `given repository when call getConversionRates method fails then return correct http failure`() =
        runBlocking {
            coEvery { datasource.getConversionRates() } throws RuntimeException()

            val result = repository.getConversionRates()

            Assert.assertEquals(Failure.HTTP_FAILURE, (result as Result.Failure).failure)
            coVerify(exactly = 1) { datasource.getConversionRates() }
        }
}
