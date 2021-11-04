package com.davidcastella.domain.transactions.interactors

import arrow.core.Either
import arrow.core.flatMap
import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import com.davidcastella.domain.conversionrates.util.getConversionRate
import com.davidcastella.domain.core.FlowUseCase
import com.davidcastella.domain.core.failure.Failure
import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.math.RoundingMode

class GetTransactions(
    private val repository: TransactionsRepository,
    private val conversionRatesRepository: ConversionRatesRepository
) : FlowUseCase<CurrencyCode, Either<Failure, List<Transaction>>> {
    override fun invoke(currencyCode: CurrencyCode): Flow<Either<Failure, List<Transaction>>> =
        repository.getTransactions()
            .combine(conversionRatesRepository.getConversionRates()) { transactionEither, conversionEither ->
                // flatMap :: Either<F, A> -> ((v: A) -> Either<F, B) -> Either<F, B>
                // map :: Either<F, A> -> ((v: A) -> B) -> Either<F, B>
                transactionEither.flatMap { transactionList ->
                    conversionEither.map { conversionList ->
                        transactionList.map {
                            val rate = conversionList.getConversionRate(it.currency, currencyCode.toString())
                            Transaction(
                                it.sku,
                                (rate * it.amount).setScale(2, RoundingMode.HALF_EVEN),
                                currencyCode.toString()
                            )
                        }
                    }
                }
            }
}