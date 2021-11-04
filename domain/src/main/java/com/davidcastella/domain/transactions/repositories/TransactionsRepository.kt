package com.davidcastella.domain.transactions.repositories

import arrow.core.Either
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.transactions.entities.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {
    fun getTransactions(): Flow<Either<Failure, List<Transaction>>>
}