package com.davidcastella.features.productlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.fold
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.interactors.GetTransactionsUseCase
import com.davidcastella.features.productlist.mappers.TransactionListMapper
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getTransactions: GetTransactionsUseCase,
    private val transactionListMapper: TransactionListMapper
) : ViewModel() {

    companion object {
        val DEFAULT_CURRENCY = CurrencyCode.EUR
    }

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()
    val viewState: LiveData<ViewState> = _viewState

    private lateinit var productList: List<ProductTransactionsUI>

    init {
        onStart()
    }

    sealed class ViewState {
        data object Initial : ViewState()
        data object Loading : ViewState()
        data object Empty : ViewState()
        class Success(val productNameList: List<ProductTransactionsUI>) : ViewState()
        class Error(val errorState: ErrorState) : ViewState()
    }

    enum class ErrorState {
        GENERIC_ERROR,
        CONNECTION_ERROR
    }

    private fun drop(state: ViewState) {
        _viewState.value = state
    }

    private fun onStart() {
        drop(ViewState.Loading)
        viewModelScope.launch {
            getTransactions(DEFAULT_CURRENCY).fold(
                isFailure = ::handleError,
                isSuccess = ::handleSuccess
            )
        }
    }

    private fun handleError(error: Failure) = when (error) {
        Failure.CONNECTION_FAILURE -> drop(ViewState.Error(ErrorState.CONNECTION_ERROR))
        else -> drop(ViewState.Error(ErrorState.GENERIC_ERROR))
    }

    private fun handleSuccess(list: List<Transaction>) {
        if (list.isEmpty()) {
            drop(ViewState.Empty)
        } else {
            productList = transactionListMapper(list)
            drop(ViewState.Success(productList))
        }
    }
}
