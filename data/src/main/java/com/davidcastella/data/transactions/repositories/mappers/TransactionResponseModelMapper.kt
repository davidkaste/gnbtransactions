package com.davidcastella.data.transactions.repositories.mappers

import com.davidcastella.data.api.models.TransactionResponseModel
import com.davidcastella.domain.transactions.entities.Transaction
import javax.inject.Inject

class TransactionResponseModelMapper @Inject constructor(): (TransactionResponseModel) -> Transaction {
    override fun invoke(trm: TransactionResponseModel) = Transaction(
        trm.sku,
        trm.amount.toBigDecimal(),
        trm.currency
    )
}