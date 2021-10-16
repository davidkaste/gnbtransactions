package com.davidcastella.domain.transactions.repositories

import com.davidcastella.domain.transactions.entities.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {
    fun getTransactions(): Flow<List<Transaction>>
}