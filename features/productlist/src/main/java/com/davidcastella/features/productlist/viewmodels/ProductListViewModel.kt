package com.davidcastella.features.productlist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.toCurrencyString
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.interactors.GetTransactions
import com.davidcastella.features.productlist.mappers.TransactionListMapper
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getTransactions: GetTransactions,
    private val transactionListMapper: TransactionListMapper
) : ViewModel() {

    companion object {
        private val DEFAULT_CURRENCY = CurrencyCode.EUR
    }

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Initial)
    val viewState: StateFlow<ViewState>
        get() = _viewState

    private lateinit var productList: List<ProductTransactionsUI>

    sealed class ViewState {
        object Initial : ViewState()
        object Loading : ViewState()
        object Empty : ViewState()
        class Success(val productNameList: List<String>) : ViewState()
        class Error(val errorState: ErrorState) : ViewState()
        class ProductDetails(
            val productName: String,
            val amounts: List<String>,
            val total: String
        ) : ViewState()
    }

    sealed class ViewEvent {
        object OnStart : ViewEvent()
        class OnProductClick(val productName: String) : ViewEvent()
    }

    enum class ErrorState {
        GENERIC_ERROR,
        CONNECTION_ERROR,
    }

    fun dispatchEvent(event: ViewEvent) = when (event) {
        is ViewEvent.OnStart -> onStart()
        is ViewEvent.OnProductClick -> onProductClick(event.productName)
    }

    private fun onStart() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            getTransactions(DEFAULT_CURRENCY)
                .collect {
                    when (it) {
                        is Either.Left -> handleError(it.value)
                        is Either.Right -> handleSuccess(it.value)
                    }
                }
        }
    }

    private fun handleError(error: Failure) = when (error) {
        Failure.CONNECTION_FAILURE -> _viewState.value =
            ViewState.Error(ErrorState.CONNECTION_ERROR)
        else -> _viewState.value = ViewState.Error(ErrorState.GENERIC_ERROR)
    }

    private fun handleSuccess(list: List<Transaction>) {
        if (list.isEmpty()) _viewState.value = ViewState.Empty
        else {
            productList = transactionListMapper(list)
            _viewState.value = ViewState.Success(productList.map { it.productSku })
        }
    }

    private fun onProductClick(productName: String) {
        val product = productList.first { it.productSku == productName }
        var total = BigDecimal.ZERO
        val amounts = product.amountList.map {
            total += it
            it.toCurrencyString(DEFAULT_CURRENCY)
        }
        _viewState.value = ViewState.ProductDetails(
            product.productSku,
            amounts,
            total.toCurrencyString(DEFAULT_CURRENCY)
        )
    }
}