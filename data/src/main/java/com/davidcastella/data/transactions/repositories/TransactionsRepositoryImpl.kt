package com.davidcastella.data.transactions.repositories

import com.davidcastella.data.transactions.datasources.remote.RemoteTransactionsDatasource
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.Result
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import java.io.IOException
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val datasource: RemoteTransactionsDatasource
) : TransactionsRepository {
    override suspend fun getTransactions(): Result<Failure, List<Transaction>> =
        try {
            val response = datasource.getTransactions()
            Result.Success(response)
        } catch (ex: IOException) {
            Result.Failure(Failure.CONNECTION_FAILURE)
        } catch (ex: RuntimeException) {
            Result.Failure(Failure.HTTP_FAILURE)
        } catch (ex: Exception) {
            Result.Failure(Failure.GENERIC_FAILURE)
        }
}
