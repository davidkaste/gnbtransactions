package com.davidcastella.data.transactions.datasources.remote

import com.davidcastella.data.api.BankService
import com.davidcastella.data.transactions.datasources.remote.mappers.TransactionResponseModelMapper
import com.davidcastella.domain.transactions.entities.Transaction
import javax.inject.Inject

class RemoteTransactionsDatasourceImpl @Inject constructor(
    private val service: BankService,
    private val mapper: TransactionResponseModelMapper
) : RemoteTransactionsDatasource {

    override suspend fun getTransactions(): List<Transaction> =
        service.getTransactions().map(mapper)
}
