package com.davidcastella.features.productlist.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davidcastella.features.productlist.R
import com.davidcastella.features.productlist.models.ProductTransactionsUI

class ProductsAdapter(private val onItemClick: (ProductTransactionsUI) -> Unit) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var productDataSet: List<ProductTransactionsUI> = listOf()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val productTextView: TextView = view.findViewById(R.id.productTextView)
        private val productContainer: View = view.findViewById(R.id.productItemContainer)

        fun bind(productName: ProductTransactionsUI, onItemClick: (ProductTransactionsUI) -> Unit) {
            productTextView.text = productName.productSku
            productContainer.setOnClickListener {
                onItemClick(productName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productDataSet[position], onItemClick)
    }

    override fun getItemCount() = productDataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(dataset: List<ProductTransactionsUI> = listOf()) {
        productDataSet = dataset
        notifyDataSetChanged()
    }
}
