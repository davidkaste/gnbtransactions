package com.davidcastella.data.transactions.datasources.remote

import com.davidcastella.data.api.BankService
import com.davidcastella.data.api.models.TransactionResponseModel
import com.davidcastella.data.transactions.datasources.GNBTransactionsDatasource
import javax.inject.Inject

class RemoteGNBTransactionsDatasource @Inject constructor(
    private val service: BankService
): GNBTransactionsDatasource {

    override suspend fun getTransactions(): List<TransactionResponseModel> = service.getTransactions()
}