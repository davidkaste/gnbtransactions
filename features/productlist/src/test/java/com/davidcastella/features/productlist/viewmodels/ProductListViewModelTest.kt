package com.davidcastella.features.productlist.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.Result
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.interactors.GetTransactionsUseCase
import com.davidcastella.features.productlist.mappers.TransactionListMapper
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    private lateinit var viewModel: ProductListViewModel

    private val useCase: GetTransactionsUseCase = mockk()
    private val mapper: TransactionListMapper = mockk()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `given viewmodel when receive start event then request transactions`() = runTest {
        ArrangeBuilder()
            .withUseCaseCallSuccess(listOf(Transaction("prod", BigDecimal(34.6), "EUR")))
            .withMapperCall(listOf(ProductTransactionsUI("prod", listOf(BigDecimal(34.6)))))

        viewModel = ProductListViewModel(useCase, mapper)

        val job = launch {
            viewModel.viewState.observeForever {
                when (it) {
                    is ProductListViewModel.ViewState.Initial -> assert(false)
                    is ProductListViewModel.ViewState.Loading -> assert(true)
                    is ProductListViewModel.ViewState.Success -> {
                        assertEquals("prod", it.productNameList.first().productSku)
                        assert(true)
                    }
                    else -> assert(false)
                }
            }
        }
        advanceUntilIdle()

        coVerify(exactly = 1) { useCase(any()) }
        verify(exactly = 1) { mapper(any()) }

        job.cancel()
    }

    @Test
    fun `given viewmodel when receive start event then request empty transactions`() = runTest {
        ArrangeBuilder()
            .withUseCaseCallSuccess(listOf())

        viewModel = ProductListViewModel(useCase, mapper)

        val job = launch {
            viewModel.viewState.observeForever {
                when (it) {
                    is ProductListViewModel.ViewState.Initial -> assert(true)
                    is ProductListViewModel.ViewState.Loading -> assert(true)
                    is ProductListViewModel.ViewState.Empty -> assert(true)
                    else -> assert(false)
                }
            }
        }
        advanceUntilIdle()

        coVerify(exactly = 1) { useCase(any()) }
        verify(exactly = 0) { mapper(any()) }

        job.cancel()
    }

    @Test
    fun `given viewmodel when receive start event fails then request generic error state`() =
        runTest {
            ArrangeBuilder()
                .withUseCaseCallFailure(Failure.GENERIC_FAILURE)

            viewModel = ProductListViewModel(useCase, mapper)

            val job = launch {
                viewModel.viewState.observeForever {
                    when (it) {
                        is ProductListViewModel.ViewState.Initial -> assert(true)
                        is ProductListViewModel.ViewState.Loading -> assert(true)
                        is ProductListViewModel.ViewState.Error -> {
                            assert(true)
                            assertEquals(
                                ProductListViewModel.ErrorState.GENERIC_ERROR,
                                it.errorState
                            )
                        }
                        else -> assert(false)
                    }
                }
            }
            advanceUntilIdle()

            coVerify(exactly = 1) { useCase(any()) }
            verify(exactly = 0) { mapper(any()) }

            job.cancel()
        }

    @Test
    fun `given viewmodel when receive start event fails then request connection error state`() =
        runTest {
            ArrangeBuilder()
                .withUseCaseCallFailure(Failure.CONNECTION_FAILURE)

            viewModel = ProductListViewModel(useCase, mapper)

            val job = launch {
                viewModel.viewState.observeForever {
                    when (it) {
                        is ProductListViewModel.ViewState.Initial -> assert(true)
                        is ProductListViewModel.ViewState.Loading -> assert(true)
                        is ProductListViewModel.ViewState.Error -> {
                            assert(true)
                            assertEquals(
                                ProductListViewModel.ErrorState.CONNECTION_ERROR,
                                it.errorState
                            )
                        }
                        else -> assert(false)
                    }
                }
            }
            advanceUntilIdle()

            coVerify(exactly = 1) { useCase(any()) }
            verify(exactly = 0) { mapper(any()) }

            job.cancel()
        }

    @Test
    fun `given viewmodel when receive on product click event then request empty transactions`() =
        runTest {
            ArrangeBuilder()
                .withUseCaseCallSuccess(listOf(Transaction("prod", BigDecimal(34.6), "EUR")))
                .withMapperCall(listOf(ProductTransactionsUI("prod", listOf(BigDecimal(34.6)))))

            viewModel = ProductListViewModel(useCase, mapper)

            val job = launch {
                viewModel.viewState.observeForever {
                    when (it) {
                        is ProductListViewModel.ViewState.Initial -> assert(true)
                        is ProductListViewModel.ViewState.Loading -> assert(true)
                        is ProductListViewModel.ViewState.Success -> assert(true)
                        else -> assert(false)
                    }
                }
            }
            advanceUntilIdle()

            job.cancel()
        }

    inner class ArrangeBuilder {
        fun withUseCaseCallSuccess(response: List<Transaction>): ArrangeBuilder {
            coEvery { useCase(any()) } returns Result.Success(response)
            return this
        }

        fun withUseCaseCallFailure(failure: Failure): ArrangeBuilder {
            coEvery { useCase(any()) } returns Result.Failure(failure)
            return this
        }

        fun withMapperCall(response: List<ProductTransactionsUI>): ArrangeBuilder {
            every { mapper(any()) } returns response
            return this
        }
    }
}
