package com.davidcastella.domain.transactions.interactors

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.Result
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class GetTransactionsTest {

    private lateinit var useCase: GetTransactions

    private val repository: TransactionsRepository = mockk()
    private val conversionRatesRepository: ConversionRatesRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetTransactions(repository, conversionRatesRepository)
    }

    @Test
    fun `given usecase when call invoke then call repository and combine result with getConversionTypes`() =
        runTest {
            ArrangeBuilder()
                .withTransactionsSuccess(getTransactionListStub())
                .withConversionRatesSuccess(getConversionListStub())

            useCase(CurrencyCode.EUR)

            coVerify(exactly = 1) { repository.getTransactions() }
            coVerify(exactly = 1) { conversionRatesRepository.getConversionRates() }
        }

    @Test
    fun `given usecase when call invoke then should return transaction list with converted amount`() =
        runTest {
            ArrangeBuilder()
                .withTransactionsSuccess(getTransactionListStub())
                .withConversionRatesSuccess(getConversionListStub())

            val expected =
                Transaction("product", BigDecimal(8).setScale(2), CurrencyCode.EUR.toString())

            val result = useCase(CurrencyCode.EUR)

            val transaction = (result as Result.Success).value.first()
            assertEquals(expected.amount, transaction.amount)
            assertEquals(expected.currency, transaction.currency)
        }

    @Test
    fun `given usecase when call invoke fails then should return failure`() =
        runTest {
            ArrangeBuilder()
                .withTransactionsFailure()
                .withConversionRatesSuccess(getConversionListStub())

            val result = useCase(CurrencyCode.EUR)

            assertEquals(Failure.GENERIC_FAILURE, (result as Result.Failure).failure)
        }

    @Test
    fun `given usecase when call invoke fails for conversionRepo then should return failure`() =
        runTest {
            ArrangeBuilder()
                .withTransactionsSuccess(getTransactionListStub())
                .withConversionRatesFailure()

            val result = useCase(CurrencyCode.EUR)

            assertEquals(Failure.GENERIC_FAILURE, (result as Result.Failure).failure)
        }

    private fun getTransactionListStub() = listOf(
        Transaction("product", BigDecimal(4), "USD")
    )

    private fun getConversionListStub() = listOf(
        ConversionRate("USD", "EUR", BigDecimal(2))
    )

    inner class ArrangeBuilder {
        fun withTransactionsSuccess(response: List<Transaction>): ArrangeBuilder {
            coEvery { repository.getTransactions() } returns Result.Success(response)
            return this
        }

        fun withTransactionsFailure(): ArrangeBuilder {
            coEvery { repository.getTransactions() } returns Result.Failure(Failure.GENERIC_FAILURE)
            return this
        }

        fun withConversionRatesSuccess(response: List<ConversionRate>): ArrangeBuilder {
            coEvery { conversionRatesRepository.getConversionRates() } returns Result.Success(response)
            return this
        }

        fun withConversionRatesFailure(): ArrangeBuilder {
            coEvery { conversionRatesRepository.getConversionRates() } returns Result.Failure(Failure.GENERIC_FAILURE)
            return this
        }
    }
}

