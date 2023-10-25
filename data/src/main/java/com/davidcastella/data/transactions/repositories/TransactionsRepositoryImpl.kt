package com.davidcastella.data.transactions.repositories

import arrow.core.Either
import com.davidcastella.data.transactions.datasources.remote.RemoteTransactionsDatasource
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val datasource: RemoteTransactionsDatasource
) : TransactionsRepository {
    override fun getTransactions(): Flow<Either<Failure, List<Transaction>>> = flow {
        try {
            val response = datasource.getTransactions()
            emit(Either.Right(response))
        } catch (ex: IOException) {
            emit(Either.Left(Failure.CONNECTION_FAILURE))
        } catch (ex: RuntimeException) {
            emit(Either.Left(Failure.HTTP_FAILURE))
        } catch (ex: Exception) {
            emit(Either.Left(Failure.GENERIC_FAILURE))
        }
    }
}
