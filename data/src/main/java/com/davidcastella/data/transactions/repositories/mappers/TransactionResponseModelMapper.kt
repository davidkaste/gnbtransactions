package com.davidcastella.data.transactions.repositories.mappers

import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.gnb_api.models.TransactionResponseModel

class TransactionResponseModelMapper: (TransactionResponseModel) -> Transaction {
    override fun invoke(trm: TransactionResponseModel) = Transaction(
        trm.sku,
        trm.amount.toBigDecimal(),
        trm.currency
    )
}