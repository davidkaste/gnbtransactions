package com.davidcastella.features.productlist.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.davidcastella.domain.core.util.toCurrencyString
import com.davidcastella.features.productlist.R
import com.davidcastella.features.productlist.adapters.ProductsAdapter
import com.davidcastella.features.productlist.databinding.FragmentProductListBinding
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import com.davidcastella.features.productlist.viewmodels.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ProductListViewModel by viewModels()

    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.viewState.collect { handleState(it) }
        }

        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnStart)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = ProductsAdapter(::onItemClick)
        with(binding) {
            productList.adapter = adapter

            val dividerItemDecoration =
                DividerItemDecoration(productList.context, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(requireActivity(), R.drawable.divider)!!
            )
            productList.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun onItemClick(product: ProductTransactionsUI) {
        val productName = product.productSku
        var total = BigDecimal.ZERO
        val amounts = product.amountList.map {
            total += it
            it.toCurrencyString(ProductListViewModel.DEFAULT_CURRENCY)
        }

        val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
            productName,
            total.toCurrencyString(ProductListViewModel.DEFAULT_CURRENCY),
            amounts.toTypedArray()
        )
        findNavController().navigate(action)
    }

    private fun handleState(state: ProductListViewModel.ViewState) = when (state) {
        is ProductListViewModel.ViewState.Initial -> {}
        is ProductListViewModel.ViewState.Loading -> setLoading(true)
        is ProductListViewModel.ViewState.Empty -> setEmpty()
        is ProductListViewModel.ViewState.Success -> setSuccess(
            state.productNameList,
            adapter
        )
        is ProductListViewModel.ViewState.Error -> setError(state.errorState)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.productsLoading.isVisible = isLoading
    }

    private fun setEmpty() {
        setLoading(false)
        binding.emptyProductsTextView.isVisible = true
        finishLoadingData()
    }

    private fun setSuccess(data: List<ProductTransactionsUI>, adapter: ProductsAdapter) {
        setLoading(false)
        binding.productList.isVisible = true
        adapter.updateData(data)
        finishLoadingData()
    }

    private fun setError(error: ProductListViewModel.ErrorState) = when (error) {
        ProductListViewModel.ErrorState.GENERIC_ERROR -> setErrorView(getString(R.string.error_generic))
        ProductListViewModel.ErrorState.CONNECTION_ERROR -> setErrorView(getString(R.string.error_connection))
    }

    private fun setErrorView(errorString: String) {
        setLoading(false)
        binding.errorProductsTextView.apply {
            text = errorString
            visibility = View.VISIBLE
        }
    }

    private fun finishLoadingData() {
        viewModel.dispatchEvent(ProductListViewModel.ViewEvent.OnFinishLoading)
    }
}
