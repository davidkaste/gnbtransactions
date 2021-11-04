package com.davidcastella.data.transactions.repositories

import arrow.core.Either
import com.davidcastella.data.transactions.datasources.GNBTransactionsDatasource
import com.davidcastella.data.transactions.repositories.mappers.TransactionResponseModelMapper
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.gnb_api.models.TransactionResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class TransactionsRepositoryImplTest {

    private lateinit var repository: TransactionsRepositoryImpl

    private val datasource: GNBTransactionsDatasource = mockk()
    private val mapper: TransactionResponseModelMapper = mockk()

    private val transactionModel = TransactionResponseModel("Product", 12.3, "EUR")
    private val transaction = Transaction("Product", 12.3.toBigDecimal(), "EUR")
    private val genericFailure = Failure.GENERIC_FAILURE

    @Before
    fun setUp() {
        coEvery { mapper.invoke(any()) } returns transaction

        repository = TransactionsRepositoryImpl(datasource, mapper)
    }

    @Test
    fun `given repository when call getTransactions method succeeds then return correct data`() = runBlocking {
        coEvery { datasource.getTransactions() } returns listOf(transactionModel)

        val result = repository.getTransactions()

        result.collect {
            assertEquals(transaction, (it as Either.Right).value.first())
        }

        coVerify(exactly = 1) { datasource.getTransactions() }
        coVerify(exactly = 1) { mapper.invoke(any()) }
    }

    @Test
    fun `given repository when call getTransactions method fails then return correct data`() = runBlocking {
        coEvery { datasource.getTransactions() } throws Exception()

        val result = repository.getTransactions()

        result.collect {
            assertEquals(genericFailure, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getTransactions() }
        coVerify(exactly = 0) { mapper.invoke(any()) }
    }
}