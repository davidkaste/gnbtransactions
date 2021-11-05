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
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class ConversionRatesRepositoryImplTest {

    private lateinit var repository: ConversionRatesRepositoryImpl

    private val datasource: GNBConversionRatesDatasource = mockk()
    private val mapper: ConversionRateResponseModelMapper = mockk()

    private val conversionRateModel = ConversionRateResponseModel("EUR", "USD", 12.3)
    private val conversionRate = ConversionRate("EUR", "USD", 12.3.toBigDecimal())

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
    fun `given repository when call getConversionRates method fails then return correct generic failure`() = runBlocking {
        coEvery { datasource.getConversionRates() } throws Exception()
        val result = repository.getConversionRates()

        result.collect {
            Assert.assertEquals(Failure.GENERIC_FAILURE, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getConversionRates() }
        coVerify(exactly = 0) { mapper.invoke(any()) }
    }

    @Test
    fun `given repository when call getConversionRates method fails then return correct connection failure`() = runBlocking {
        coEvery { datasource.getConversionRates() } throws UnknownHostException()
        val result = repository.getConversionRates()

        result.collect {
            Assert.assertEquals(Failure.CONNECTION_FAILURE, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getConversionRates() }
        coVerify(exactly = 0) { mapper.invoke(any()) }
    }

    @Test
    fun `given repository when call getConversionRates method fails then return correct http failure`() = runBlocking {
        coEvery { datasource.getConversionRates() } throws
                HttpException(
                    Response.error<String>(
                        404,
                        ResponseBody.create(MediaType.parse(""), "")))

        val result = repository.getConversionRates()

        result.collect {
            Assert.assertEquals(Failure.HTTP_FAILURE, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getConversionRates() }
        coVerify(exactly = 0) { mapper.invoke(any()) }
    }
}