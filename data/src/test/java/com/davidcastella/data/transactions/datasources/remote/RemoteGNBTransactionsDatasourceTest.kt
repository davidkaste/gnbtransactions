package com.davidcastella.data.transactions.datasources.remote

import com.davidcastella.gnb_api.BankServiceAPI
import com.davidcastella.gnb_api.models.TransactionResponseModel
import com.davidcastella.gnb_api.service.GNBankService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

internal class RemoteGNBTransactionsDatasourceTest {

    private lateinit var datasource: RemoteGNBTransactionsDatasource

    private val service: BankServiceAPI = mockk()
    private val retrofit: Retrofit = mockk()
    private val api: GNBankService = mockk()

    @Before
    fun setUp() {
        coEvery { service.api } returns retrofit
        coEvery { retrofit.create(GNBankService::class.java) } returns api
        datasource = RemoteGNBTransactionsDatasource(service)
    }

    @Test
    fun getTransactions(): Unit = runBlocking {
        val expected = TransactionResponseModel("123", 12.3, "EUR")
        ArrangeBuilder().withGetTransactionsSuccess(listOf(expected))

        val t = datasource.getTransactions()

        coVerify(exactly = 1) { api.getTransactions() }

        val result = t[0]
        assertEquals(expected.amount, result.amount, 0.0)
        assertEquals(expected.currency, result.currency)
        assertEquals(expected.sku, result.sku)
    }

    inner class ArrangeBuilder {

        fun withGetTransactionsSuccess(model: List<TransactionResponseModel>): ArrangeBuilder {
            coEvery { api.getTransactions() } returns model
            return this
        }
    }
}