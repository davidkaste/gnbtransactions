package com.davidcastella.features.productlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.transactions.interactors.GetTransactions
import com.davidcastella.features.productlist.mappers.TransactionListMapper
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getTransactions: GetTransactions,
    private val transactionListMapper: TransactionListMapper
) : ViewModel() {

    companion object {
        private val DEFAULT_CURRENCY = CurrencyCode.EUR
    }

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()
    val viewState: LiveData<ViewState>
        get() = _viewState

    sealed class ViewState {
        object Loading : ViewState()
        object Empty : ViewState()
        class Success(val data: List<ProductTransactionsUI>) : ViewState()
    }

    sealed class ViewEvent {
        object Start: ViewEvent()
    }

    fun dispatchEvent(event: ViewEvent) = when(event) {
        is ViewEvent.Start -> onStart()
    }

    private fun onStart() {
        _viewState.postValue(ViewState.Loading)
        viewModelScope.launch {
            getTransactions(DEFAULT_CURRENCY)
                .collect {
                    val productList = transactionListMapper(it)
                    _viewState.postValue(ViewState.Success(productList))
                }
        }
    }
}