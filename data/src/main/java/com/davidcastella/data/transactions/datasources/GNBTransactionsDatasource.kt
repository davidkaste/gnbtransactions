package com.davidcastella.data.transactions.datasources

import com.davidcastella.gnb_api.models.ConversionRateResponseModel
import com.davidcastella.gnb_api.models.TransactionResponseModel

interface GNBTransactionsDatasource {
    suspend fun getTransactions(): List<TransactionResponseModel>
    suspend fun getConversionRates(): List<ConversionRateResponseModel>
}