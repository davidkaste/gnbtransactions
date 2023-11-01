package com.davidcastella.domain.transactions.interactors

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import com.davidcastella.domain.conversionrates.util.getConversionRate
import com.davidcastella.domain.core.SuspendUseCase
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.core.util.Result
import com.davidcastella.domain.core.util.flatMap
import com.davidcastella.domain.core.util.map
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import javax.inject.Inject

class GetTransactions @Inject constructor(
    private val repository: TransactionsRepository,
    private val conversionRatesRepository: ConversionRatesRepository
) : SuspendUseCase<CurrencyCode, Result<Failure, List<Transaction>>> {

    override suspend fun invoke(currencyCode: CurrencyCode): Result<Failure, List<Transaction>> =
        withContext(Dispatchers.IO) {
            repository.getTransactions().flatMap { transactionList ->
                conversionRatesRepository.getConversionRates().map { conversionList ->
                    transactionList.map {
                        convertTransactionAmount(conversionList, it, currencyCode.toString())
                    }
                }
            }
        }

    private fun convertTransactionAmount(
        conversionList: List<ConversionRate>,
        transaction: Transaction,
        currencyCode: String
    ): Transaction {
        val rate = conversionList.getConversionRate(transaction.currency, currencyCode)
        return Transaction(
            transaction.sku,
            (rate * transaction.amount).setScale(2, RoundingMode.HALF_EVEN),
            currencyCode
        )
    }
}