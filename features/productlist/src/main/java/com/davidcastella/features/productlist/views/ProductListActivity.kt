package com.davidcastella.features.productlist.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.davidcastella.features.productlist.R
import com.davidcastella.features.productlist.adapters.ProductsAdapter
import com.davidcastella.features.productlist.databinding.ActivityProductListBinding
import com.davidcastella.features.productlist.viewmodels.ProductListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT_NAME_KEY = "productName"
        const val AMOUNT_LIST_KEY = "amountList"
        const val TOTAL_LIST_KEY = "total"
    }

    private val viewModel: ProductListViewModel by viewModel()

    private val binding: ActivityProductListBinding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
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
                is ProductListViewModel.ViewState.ProductDetails -> openProductDetailsScreen(it.productName, it.amounts, it.total)
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

    private fun openProductDetailsScreen(productName: String, amounts: List<String>, total: String) {
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

}