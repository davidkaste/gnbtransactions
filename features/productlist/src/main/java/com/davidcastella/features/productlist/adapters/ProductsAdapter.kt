package com.davidcastella.features.productlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davidcastella.features.productlist.R

class ProductsAdapter(private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var productDataSet: List<String> = listOf()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val productTextView: TextView = view.findViewById(R.id.productTextView)
        private val productContainer: View = view.findViewById(R.id.productItemContainer)

        fun bind(productName: String, onItemClick: (String) -> Unit) {
            productTextView.text = productName
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

    fun updateData(dataset: List<String> = listOf()) {
        productDataSet = dataset
        notifyDataSetChanged()
    }
}