package com.davidcastella.features.productlist.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.davidcastella.features.productlist.R
import com.davidcastella.features.productlist.adapters.ProductsAdapter
import com.davidcastella.features.productlist.databinding.ActivityProductListBinding
import com.davidcastella.features.productlist.viewmodels.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT_NAME_KEY = "productName"
        const val AMOUNT_LIST_KEY = "amountList"
        const val TOTAL_LIST_KEY = "total"
    }

    private lateinit var viewModel: ProductListViewModel

    private val binding: ActivityProductListBinding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]

        setupRecyclerView()

        viewModel.viewState.observe(this) {
            when (it) {
                is ProductListViewModel.ViewState.Loading -> setLoading(true)
                is ProductListViewModel.ViewState.Empty -> setEmpty()
                is ProductListViewModel.ViewState.Success -> setSuccess(it.productNameList, adapter)
                is ProductListViewModel.ViewState.ProductDetails -> openProductDetailsScreen(
                    it.productName,
                    it.amounts,
                    it.total
                )
                is ProductListViewModel.ViewState.Error -> setError(it.errorState)
            }
        }

        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnStart)
    }

    private fun setupRecyclerView() {
        adapter = ProductsAdapter(::onItemClick)
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

    private fun onItemClick(product: String) {
        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnProductClick(product))
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

    private fun openProductDetailsScreen(
        productName: String,
        amounts: List<String>,
        total: String
    ) {
        val intent = Intent(baseContext, ProductDetailActivity::class.java).apply {
            val bundle = Bundle().apply {
                putString(TOTAL_LIST_KEY, total)
                putStringArrayList(AMOUNT_LIST_KEY, amounts.toCollection(ArrayList<String>()))
                putString(PRODUCT_NAME_KEY, productName)
            }
            putExtras(bundle)
        }

        startActivity(intent)
    }

    private fun setError(error: ProductListViewModel.ErrorState) = when (error) {
        ProductListViewModel.ErrorState.GENERIC_ERROR -> setErrorView(getString(R.string.error_generic))
        ProductListViewModel.ErrorState.CONNECTION_ERROR -> setErrorView(getString(R.string.error_connection))
    }

    private fun setErrorView(errorString: String) {
        binding.productsLoading.visibility = View.GONE
        binding.errorProductsTextView.apply {
            text = errorString
            visibility = View.VISIBLE
        }
    }

}