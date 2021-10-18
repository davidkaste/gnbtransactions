package com.davidcastella.features.productlist.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.davidcastella.features.productlist.R
import com.davidcastella.features.productlist.adapters.ProductsAdapter
import com.davidcastella.features.productlist.databinding.ActivityTransactionListBinding
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import com.davidcastella.features.productlist.viewmodels.ProductListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListActivity : AppCompatActivity() {

    private val viewModel: ProductListViewModel by viewModel()

    private val binding: ActivityTransactionListBinding by lazy {
        ActivityTransactionListBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.viewState.observe(this) {
            when (it) {
                is ProductListViewModel.ViewState.Loading -> setLoading(true)
                is ProductListViewModel.ViewState.Empty -> setEmpty()
                is ProductListViewModel.ViewState.Success -> setSuccess(it.productNameList, adapter)
                is ProductListViewModel.ViewState.ProductDetails -> openProductDetailsScreen(it.productTransactions)
            }
        }

        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnStart)
    }

    private fun setupRecyclerView() {
        adapter = ProductsAdapter()
        binding.productList.adapter = adapter

        val dividerItemDecoration =
            DividerItemDecoration(binding.productList.context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                this@ProductListActivity,
                R.drawable.divider
            )!!
        )
        binding.productList.addItemDecoration(dividerItemDecoration)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.productsLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setEmpty() {
        setLoading(false)
        binding.emptyProductsTextView.visibility = View.VISIBLE
    }

    private fun setSuccess(data: List<String>, adapter: ProductsAdapter) {
        setLoading(false)
        binding.productList.visibility = View.VISIBLE
        adapter.updateData(data)
    }

    private fun openProductDetailsScreen(data: ProductTransactionsUI) {
    }

}