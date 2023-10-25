package com.davidcastella.data.transactions.repositories

import arrow.core.Either
import com.davidcastella.data.transactions.datasources.remote.RemoteTransactionsDatasource
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.transactions.entities.Transaction
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TransactionsRepositoryImplTest {

    private lateinit var repository: TransactionsRepositoryImpl

    private val datasource: RemoteTransactionsDatasource = mockk()

    private val transaction = Transaction("Product", 12.3.toBigDecimal(), "EUR")

    @Before
    fun setUp() {
        repository = TransactionsRepositoryImpl(datasource)
    }

    @Test
    fun `given repository when call getTransactions method succeeds then return correct data`() = runBlocking {
        coEvery { datasource.getTransactions() } returns listOf(transaction)

        val result = repository.getTransactions()

        result.collect {
            assertEquals(transaction, (it as Either.Right).value.first())
        }

        coVerify(exactly = 1) { datasource.getTransactions() }
    }

    @Test
    fun `given repository when call getTransactions method fails then return correct generic failure`() = runBlocking {
        coEvery { datasource.getTransactions() } throws Exception()

        val result = repository.getTransactions()

        result.collect {
            assertEquals(Failure.GENERIC_FAILURE, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getTransactions() }
    }

    @Test
    fun `given repository when call getTransactions method fails then return correct connection failure`() = runBlocking {
        coEvery { datasource.getTransactions() } throws IOException()

        val result = repository.getTransactions()

        result.collect {
            assertEquals(Failure.CONNECTION_FAILURE, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getTransactions() }
    }

    @Test
    fun `given repository when call getTransactions method fails then return correct http failure`() = runBlocking {
        coEvery { datasource.getTransactions() } throws RuntimeException()

        val result = repository.getTransactions()

        result.collect {
            assertEquals(Failure.HTTP_FAILURE, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getTransactions() }
    }
}
