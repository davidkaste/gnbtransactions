package com.davidcastella.data.transactions.datasources.remote

import com.davidcastella.data.transactions.datasources.GNBTransactionsDatasource
import com.davidcastella.gnb_api.TransactionServiceAPI
import com.davidcastella.gnb_api.models.ConversionRateResponseModel
import com.davidcastella.gnb_api.models.TransactionResponseModel
import com.davidcastella.gnb_api.service.GNBTransactionService

class RemoteGNBTransactionsDatasource(
    private val serviceApi: TransactionServiceAPI
): GNBTransactionsDatasource {

    private val service = serviceApi.api.create(GNBTransactionService::class.java)

    override suspend fun getTransactions(): List<TransactionResponseModel> = service.getTransactions()

    override suspend fun getConversionRates(): List<ConversionRateResponseModel> = service.getCurrencyRates()
}