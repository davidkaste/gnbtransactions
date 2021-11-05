package com.davidcastella.data.transactions.repositories.mappers

import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.gnb_api.models.TransactionResponseModel
import javax.inject.Inject

class TransactionResponseModelMapper @Inject constructor(): (TransactionResponseModel) -> Transaction {
    override fun invoke(trm: TransactionResponseModel) = Transaction(
        trm.sku,
        trm.amount.toBigDecimal(),
        trm.currency
    )
}