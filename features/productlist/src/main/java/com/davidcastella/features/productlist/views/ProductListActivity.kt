package com.davidcastella.features.productlist.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davidcastella.features.productlist.databinding.ActivityTransactionListBinding
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import com.davidcastella.features.productlist.viewmodels.ProductListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListActivity : AppCompatActivity() {

    private val viewModel: ProductListViewModel by viewModel()

    private val binding: ActivityTransactionListBinding by lazy {
        ActivityTransactionListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.viewState.observe(this) {
            when(it) {
                is ProductListViewModel.ViewState.Loading -> setLoading()
                is ProductListViewModel.ViewState.Empty -> setEmpty()
                is ProductListViewModel.ViewState.Success -> setSuccess(it.data)
            }
        }

        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.Start)
    }

    private fun setLoading() {
        2+2
    }

    private fun setEmpty() {

    }

    private fun setSuccess(data: List<ProductTransactionsUI>) {
        2+2
    }

}