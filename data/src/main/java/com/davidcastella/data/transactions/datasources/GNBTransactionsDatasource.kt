package com.davidcastella.data.transactions.datasources

import com.davidcastella.data.api.models.TransactionResponseModel

interface GNBTransactionsDatasource {
    suspend fun getTransactions(): List<TransactionResponseModel>
}