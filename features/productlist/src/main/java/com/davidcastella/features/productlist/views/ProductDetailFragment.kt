package com.davidcastella.features.productlist.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.davidcastella.features.productlist.R
import com.davidcastella.features.productlist.adapters.ProductDetailsAdapter
import com.davidcastella.features.productlist.databinding.FragmentProductDetailBinding

class ProductDetailFragment: Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding
        get() = _binding!!

    val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val amounts = args.amounts
        val total = args.total

        setupRecyclerView(amounts.toList(), total)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView(
        amountList: List<String>,
        total: String
    ) {
        val adapter = ProductDetailsAdapter(amountList, total)
        binding.also {
            it.productDetailList.adapter = adapter
            val dividerItemDecoration =
                DividerItemDecoration(it.productDetailList.context, DividerItemDecoration.VERTICAL)
            dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.divider
                )!!
            )
            it.productDetailList.addItemDecoration(dividerItemDecoration)
        }
    }
}
