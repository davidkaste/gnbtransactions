package com.davidcastella.domain.transactions.repositories

import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.Result
import com.davidcastella.domain.transactions.entities.Transaction

interface TransactionsRepository {
    suspend fun getTransactions(): Result<Failure, List<Transaction>>
}