package com.davidcastella.domain.transactions.interactors

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.conversionrates.interactors.GetConversionRates
import com.davidcastella.domain.core.NoParams
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class GetTransactionsTest {

    private lateinit var useCase: GetTransactions

    private val repository: TransactionsRepository = mockk()
    private val getConversionRates: GetConversionRates = mockk()

    @Before
    fun setUp() {
        val transactionList: List<Transaction> = listOf(
            Transaction("product", BigDecimal(4), "USD")
        )

        val conversionRates = listOf(
            ConversionRate("USD", "EUR", BigDecimal(2))
        )

        coEvery { repository.getTransactions() } returns flow { emit(transactionList) }
        coEvery { getConversionRates(NoParams) } returns flow { emit(conversionRates) }

        useCase = GetTransactions(repository, getConversionRates)
    }

    @Test
    fun `given usecase when call invoke then call repository and combine result with getConversionTypes`() =
        runBlocking {
            useCase(CurrencyCode.EUR)

            coVerify(exactly = 1) { repository.getTransactions() }
            coVerify(exactly = 1) { getConversionRates(NoParams) }
        }

    @Test
    fun `given usecase when call invoke then should return transaction list with converted amount`() =
        runBlocking {
            val expected =
                Transaction("product", BigDecimal(8).setScale(2), CurrencyCode.EUR.toString())

            val result = useCase(CurrencyCode.EUR)

            result.collect {
                val transaction = it.first()
                assertEquals(expected.amount, transaction.amount)
                assertEquals(expected.currency, transaction.currency)
            }
        }
}

