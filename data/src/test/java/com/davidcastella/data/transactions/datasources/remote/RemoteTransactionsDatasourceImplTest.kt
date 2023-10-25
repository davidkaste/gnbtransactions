package com.davidcastella.data.transactions.datasources.remote

import com.davidcastella.data.api.BankService
import com.davidcastella.data.api.models.TransactionResponseModel
import com.davidcastella.data.transactions.datasources.remote.mappers.TransactionResponseModelMapper
import com.davidcastella.domain.transactions.entities.Transaction
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class RemoteTransactionsDatasourceImplTest {

    private lateinit var datasource: RemoteTransactionsDatasourceImpl

    private val service: BankService = mockk()
    private val mapper: TransactionResponseModelMapper = mockk()
    
    private val transaction = Transaction("Product", 12.3.toBigDecimal(), "EUR")

    @Before
    fun setUp() {
        coEvery { mapper.invoke(any()) } returns transaction

        datasource = RemoteTransactionsDatasourceImpl(service, mapper)
    }

    @Test
    fun getTransactions(): Unit = runBlocking {
        val expected = TransactionResponseModel("123", 12.3, "EUR")
        ArrangeBuilder().withGetTransactionsSuccess(listOf(expected))

        val t = datasource.getTransactions()

        coVerify(exactly = 1) { service.getTransactions() }

        val result = t[0]
        assertEquals(transaction.amount, result.amount)
        assertEquals(transaction.currency, result.currency)
        assertEquals(transaction.sku, result.sku)
    }

    inner class ArrangeBuilder {

        fun withGetTransactionsSuccess(model: List<TransactionResponseModel>): ArrangeBuilder {
            coEvery { service.getTransactions() } returns model
            return this
        }
    }
}
