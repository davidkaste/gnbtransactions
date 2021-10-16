package com.davidcastella.domain.conversionrates.interactors

import com.davidcastella.domain.NoParams
import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrencyRatesTest {

    private lateinit var useCase: GetConversionRates

    private val repository: ConversionRatesRepository = mockk()

    private val conversionRate = ConversionRate("USD", "EUR", 12.3.toBigDecimal())

    @Before
    fun setUp() {
        coEvery { repository.getConversionRates() } returns flow {
            emit(listOf(conversionRate))
        }
        useCase = GetConversionRates(repository)
    }

    @Test
    operator fun invoke() = runBlocking {
        val result = useCase(NoParams)

        result.collect {
            assertEquals(conversionRate, it.first())
        }

        coVerify(exactly = 1) { repository.getConversionRates() }
    }
}