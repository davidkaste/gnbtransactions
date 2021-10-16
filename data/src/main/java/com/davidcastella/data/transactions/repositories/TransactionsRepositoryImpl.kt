package com.davidcastella.data.transactions.repositories

import com.davidcastella.data.transactions.datasources.GNBTransactionsDatasource
import com.davidcastella.data.transactions.repositories.mappers.TransactionResponseModelMapper
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionsRepositoryImpl(
    private val datasource: GNBTransactionsDatasource,
    private val transactionMapper: TransactionResponseModelMapper
): TransactionsRepository {
    override fun getTransactions(): Flow<List<Transaction>> = flow {
        val response = datasource.getTransactions()
        emit(response.map(transactionMapper))
    }
}