package com.davidcastella.features.productlist.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.davidcastella.features.productlist.R
import com.davidcastella.features.productlist.adapters.ProductDetailsAdapter
import com.davidcastella.features.productlist.databinding.ActivityProductDetailBinding
import com.davidcastella.features.productlist.views.ProductListActivity.Companion.AMOUNT_LIST_KEY
import com.davidcastella.features.productlist.views.ProductListActivity.Companion.PRODUCT_NAME_KEY
import com.davidcastella.features.productlist.views.ProductListActivity.Companion.TOTAL_LIST_KEY
import java.util.ArrayList

class ProductDetailActivity : AppCompatActivity() {

    private val binding: ActivityProductDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bundle = intent.extras

        val name = intent?.getStringExtra(PRODUCT_NAME_KEY) ?: ""
        val total = bundle?.getString(TOTAL_LIST_KEY) ?: ""
        val amountList = bundle?.getStringArrayList(AMOUNT_LIST_KEY) ?: arrayListOf<String>()

        setupRecyclerView(amountList, total)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(name)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView(
        amountList: ArrayList<String>,
        total: String
    ) {
        val adapter = ProductDetailsAdapter(amountList.toList(), total)
        binding.productDetailList.adapter = adapter
        val dividerItemDecoration =
            DividerItemDecoration(binding.productDetailList.context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                this@ProductDetailActivity,
                R.drawable.divider
            )!!
        )
        binding.productDetailList.addItemDecoration(dividerItemDecoration)
    }
}