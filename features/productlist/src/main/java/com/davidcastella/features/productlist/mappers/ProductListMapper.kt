package com.davidcastella.features.productlist.mappers

import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.features.productlist.models.ProductTransactionsUI

class TransactionListMapper : (List<Transaction>) -> List<ProductTransactionsUI> {
    override fun invoke(entity: List<Transaction>): List<ProductTransactionsUI> = entity
        .groupBy { it.sku }
        .map { ProductTransactionsUI(it.key, it.value.map(Transaction::amount)) }
}