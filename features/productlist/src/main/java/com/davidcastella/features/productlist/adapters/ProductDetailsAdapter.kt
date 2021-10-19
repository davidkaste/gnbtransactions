package com.davidcastella.features.productlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davidcastella.features.productlist.R

class ProductDetailsAdapter(
    private val amounts: List<String>,
    private val total: String
) : RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder>() {

    companion object {
        private const val AMOUNT_VIEW = 0
        private const val TOTAL_VIEW = 1
    }

    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class AmountViewHolder(view: View) : ViewHolder(view) {
        private val amountTextView: TextView = view.findViewById(R.id.amountTextView)
        fun bind(value: String) {
            amountTextView.text = value
        }
    }

    class TotalViewHolder(view: View) : ViewHolder(view) {
        private val totalTextView: TextView = view.findViewById(R.id.totalValueTextView)
        fun bind(value: String) {
            totalTextView.text = value
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position == amounts.size) TOTAL_VIEW
        else AMOUNT_VIEW

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        if (viewType == AMOUNT_VIEW) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.amount_item_list, parent, false)
            AmountViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.total_item_list, parent, false)
            TotalViewHolder(view)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == amounts.size) {
            (holder as TotalViewHolder).bind(total)
        } else {
            (holder as AmountViewHolder).bind(amounts[position])
        }
    }

    override fun getItemCount() = amounts.size + 1
}