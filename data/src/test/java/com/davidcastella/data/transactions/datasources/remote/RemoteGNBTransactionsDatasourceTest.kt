package com.davidcastella.data.transactions.datasources.remote

import com.davidcastella.data.api.BankService
import com.davidcastella.data.api.models.TransactionResponseModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class RemoteGNBTransactionsDatasourceTest {

    private lateinit var datasource: RemoteGNBTransactionsDatasource

    private val service: BankService = mockk()

    @Before
    fun setUp() {
        datasource = RemoteGNBTransactionsDatasource(service)
    }

    @Test
    fun getTransactions(): Unit = runBlocking {
        val expected = TransactionResponseModel("123", 12.3, "EUR")
        ArrangeBuilder().withGetTransactionsSuccess(listOf(expected))

        val t = datasource.getTransactions()

        coVerify(exactly = 1) { service.getTransactions() }

        val result = t[0]
        assertEquals(expected.amount, result.amount, 0.0)
        assertEquals(expected.currency, result.currency)
        assertEquals(expected.sku, result.sku)
    }

    inner class ArrangeBuilder {

        fun withGetTransactionsSuccess(model: List<TransactionResponseModel>): ArrangeBuilder {
            coEvery { service.getTransactions() } returns model
            return this
        }
    }
}