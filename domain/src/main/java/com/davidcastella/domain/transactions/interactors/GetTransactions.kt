package com.davidcastella.domain.transactions.interactors

import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import com.davidcastella.domain.conversionrates.util.getConversionRate
import com.davidcastella.domain.core.FlowUseCase
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.math.RoundingMode

class GetTransactions(
    private val repository: TransactionsRepository,
    private val conversionRatesRepository: ConversionRatesRepository
) : FlowUseCase<CurrencyCode, List<Transaction>> {
    override fun invoke(currencyCode: CurrencyCode): Flow<List<Transaction>> =
        repository.getTransactions()
            .combine(conversionRatesRepository.getConversionRates()) { transactionList, conversionRates ->
                transactionList.map {
                    if (it.currency == currencyCode.toString()) return@map it
                    val rate =
                        conversionRates.getConversionRate(it.currency, currencyCode.toString())
                    Transaction(
                        it.sku,
                        (rate * it.amount).setScale(2, RoundingMode.HALF_EVEN),
                        currencyCode.toString()
                    )
                }
            }
}