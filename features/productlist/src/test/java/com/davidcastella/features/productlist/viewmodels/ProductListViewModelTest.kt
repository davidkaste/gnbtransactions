package com.davidcastella.features.productlist.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.toCurrencyString
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.interactors.GetTransactions
import com.davidcastella.features.productlist.mappers.TransactionListMapper
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.math.BigDecimal

class ProductListViewModelTest {

    private lateinit var viewModel: ProductListViewModel

    private val useCase: GetTransactions = mockk()
    private val mapper: TransactionListMapper = mockk()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val dispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = ProductListViewModel(useCase, mapper)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given viewmodel when receive start event then request transactions`() = runBlockingTest {
        ArrangeBuilder()
            .withUseCaseCallSuccess(listOf(Transaction("prod", BigDecimal(34.6), "EUR")))
            .withMapperCall(listOf(ProductTransactionsUI("prod", listOf(BigDecimal(34.6)))))

        val job = launch {
            viewModel.viewState.collect {
                when(it) {
                    is ProductListViewModel.ViewState.Initial -> assert(true)
                    is ProductListViewModel.ViewState.Loading -> assert(true)
                    is ProductListViewModel.ViewState.Success -> {
                        assertEquals("prod", it.productNameList.first().productSku)
                        assert(true)
                    }
                    else -> assert(false)
                }
            }
        }

        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnStart)

        verify (exactly = 1) { useCase(any()) }
        verify (exactly = 1) { mapper(any()) }

        job.cancel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given viewmodel when receive start event then request empty transactions`() = runBlockingTest {
        ArrangeBuilder()
            .withUseCaseCallSuccess(listOf())

        val job = launch {
            viewModel.viewState.collect {
                when(it) {
                    is ProductListViewModel.ViewState.Initial -> assert(true)
                    is ProductListViewModel.ViewState.Loading -> assert(true)
                    is ProductListViewModel.ViewState.Empty -> assert(true)
                    else -> assert(false)
                }
            }
        }

        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnStart)

        verify (exactly = 1) { useCase(any()) }
        verify (exactly = 0) { mapper(any()) }

        job.cancel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given viewmodel when receive start event fails then request generic error state`() = runBlockingTest {
        ArrangeBuilder()
            .withUseCaseCallFailure(Failure.GENERIC_FAILURE)

        val job = launch {
            viewModel.viewState.collect {
                when(it) {
                    is ProductListViewModel.ViewState.Initial -> assert(true)
                    is ProductListViewModel.ViewState.Loading -> assert(true)
                    is ProductListViewModel.ViewState.Error -> {
                        assert(true)
                        assertEquals(ProductListViewModel.ErrorState.GENERIC_ERROR, it.errorState)
                    }
                    else -> assert(false)
                }
            }
        }

        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnStart)

        verify (exactly = 1) { useCase(any()) }
        verify (exactly = 0) { mapper(any()) }

        job.cancel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given viewmodel when receive start event fails then request connection error state`() = runBlockingTest {
        ArrangeBuilder()
            .withUseCaseCallFailure(Failure.CONNECTION_FAILURE)

        val job = launch {
            viewModel.viewState.collect {
                when(it) {
                    is ProductListViewModel.ViewState.Initial -> assert(true)
                    is ProductListViewModel.ViewState.Loading -> assert(true)
                    is ProductListViewModel.ViewState.Error -> {
                        assert(true)
                        assertEquals(ProductListViewModel.ErrorState.CONNECTION_ERROR, it.errorState)
                    }
                    else -> assert(false)
                }
            }
        }

        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnStart)

        verify (exactly = 1) { useCase(any()) }
        verify (exactly = 0) { mapper(any()) }

        job.cancel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given viewmodel when receive on product click event then request empty transactions`() = runBlockingTest {
        ArrangeBuilder()
            .withUseCaseCallSuccess(listOf(Transaction("prod", BigDecimal(34.6), "EUR")))
            .withMapperCall(listOf(ProductTransactionsUI("prod", listOf(BigDecimal(34.6)))))

        val job = launch {
            viewModel.viewState.collect {
                when(it) {
                    is ProductListViewModel.ViewState.Initial -> assert(true)
                    is ProductListViewModel.ViewState.Loading -> assert(true)
                    is ProductListViewModel.ViewState.Success -> assert(true)
                    else -> assert(false)
                }
            }
        }

        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnStart)

        job.cancel()
    }

    inner class ArrangeBuilder {
        fun withUseCaseCallSuccess(response: List<Transaction>): ArrangeBuilder {
            every { useCase(any()) } returns flow { emit(Either.Right(response)) }
            return this
        }

        fun withUseCaseCallFailure(failure: Failure): ArrangeBuilder {
            every { useCase(any()) } returns flow { emit(Either.Left(failure)) }
            return this
        }

        fun withMapperCall(response: List<ProductTransactionsUI>): ArrangeBuilder {
            every { mapper(any()) } returns response
            return this
        }
    }
}
