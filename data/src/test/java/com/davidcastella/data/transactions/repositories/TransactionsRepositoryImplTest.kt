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
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class TransactionsRepositoryImplTest {

    private lateinit var repository: TransactionsRepositoryImpl

    private val datasource: GNBTransactionsDatasource = mockk()
    private val mapper: TransactionResponseModelMapper = mockk()

    private val transactionModel = TransactionResponseModel("Product", 12.3, "EUR")
    private val transaction = Transaction("Product", 12.3.toBigDecimal(), "EUR")

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
    fun `given repository when call getTransactions method fails then return correct generic failure`() = runBlocking {
        coEvery { datasource.getTransactions() } throws Exception()

        val result = repository.getTransactions()

        result.collect {
            assertEquals(Failure.GENERIC_FAILURE, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getTransactions() }
        coVerify(exactly = 0) { mapper.invoke(any()) }
    }

    @Test
    fun `given repository when call getTransactions method fails then return correct connection failure`() = runBlocking {
        coEvery { datasource.getTransactions() } throws UnknownHostException()

        val result = repository.getTransactions()

        result.collect {
            assertEquals(Failure.CONNECTION_FAILURE, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getTransactions() }
        coVerify(exactly = 0) { mapper.invoke(any()) }
    }

    @Test
    fun `given repository when call getTransactions method fails then return correct http failure`() = runBlocking {
        coEvery { datasource.getTransactions() } throws
                HttpException(
                    Response.error<String>(
                        404,
                        ResponseBody.create(MediaType.parse(""), "")))

        val result = repository.getTransactions()

        result.collect {
            assertEquals(Failure.HTTP_FAILURE, (it as Either.Left).value)
        }

        coVerify(exactly = 1) { datasource.getTransactions() }
        coVerify(exactly = 0) { mapper.invoke(any()) }
    }
}