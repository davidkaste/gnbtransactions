package com.davidcastella.features.productlist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.interactors.GetTransactions
import com.davidcastella.features.productlist.mappers.TransactionListMapper
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getTransactions: GetTransactions,
    private val transactionListMapper: TransactionListMapper
) : ViewModel() {

    companion object {
        val DEFAULT_CURRENCY = CurrencyCode.EUR
    }

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Initial)
    val viewState: StateFlow<ViewState>
        get() = _viewState

    private lateinit var productList: List<ProductTransactionsUI>
    private var hasToRequestData: Boolean = true

    sealed class ViewState {
        object Initial : ViewState()
        object Loading : ViewState()
        object Empty : ViewState()
        class Success(val productNameList: List<ProductTransactionsUI>) : ViewState()
        class Error(val errorState: ErrorState) : ViewState()
    }

    sealed class ViewEvent {
        object OnStart : ViewEvent()
        object OnFinishLoading : ViewEvent()
    }

    enum class ErrorState {
        GENERIC_ERROR,
        CONNECTION_ERROR,
    }

    fun dispatchEvent(event: ViewEvent) = when (event) {
        is ViewEvent.OnStart -> onStart()
        is ViewEvent.OnFinishLoading -> onFinishLoading()
    }

    private fun drop(state: ViewState) {
        _viewState.value = state
    }

    private fun onStart() {
        if (!hasToRequestData) return
        drop(ViewState.Loading)
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

    private fun onFinishLoading() {
        hasToRequestData = false
    }

    private fun handleError(error: Failure) = when (error) {
        Failure.CONNECTION_FAILURE -> drop(ViewState.Error(ErrorState.CONNECTION_ERROR))
        else -> drop(ViewState.Error(ErrorState.GENERIC_ERROR))
    }

    private fun handleSuccess(list: List<Transaction>) {
        if (list.isEmpty()) drop(ViewState.Empty)
        else {
            productList = transactionListMapper(list)
            drop(ViewState.Success(productList))
        }
    }
}
