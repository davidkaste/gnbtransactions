package com.davidcastella.data.transactions.datasources.remote

import com.davidcastella.domain.transactions.entities.Transaction

interface RemoteTransactionsDatasource {
    suspend fun getTransactions(): List<Transaction>
}
