package com.davidcastella.data.transactions.repositories

import arrow.core.Either
import com.davidcastella.data.transactions.datasources.GNBTransactionsDatasource
import com.davidcastella.data.transactions.repositories.mappers.TransactionResponseModelMapper
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionsRepositoryImpl(
    private val datasource: GNBTransactionsDatasource,
    private val transactionMapper: TransactionResponseModelMapper
): TransactionsRepository {
    override fun getTransactions(): Flow<Either<Failure, List<Transaction>>> = flow {
        try {
            val response = datasource.getTransactions()
            emit(Either.Right(response.map(transactionMapper)))
        } catch (ex: Exception) {
            emit(Either.Left(Failure.GENERIC_FAILURE))
        }
    }
}