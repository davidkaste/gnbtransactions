package com.davidcastella.data.transactions.datasources.remote

import com.davidcastella.data.transactions.datasources.GNBTransactionsDatasource
import com.davidcastella.gnb_api.BankServiceAPI
import com.davidcastella.gnb_api.models.TransactionResponseModel
import com.davidcastella.gnb_api.service.GNBankService

class RemoteGNBTransactionsDatasource(
    serviceApi: BankServiceAPI
): GNBTransactionsDatasource {

    private val service = serviceApi.api.create(GNBankService::class.java)

    override suspend fun getTransactions(): List<TransactionResponseModel> = service.getTransactions()
}