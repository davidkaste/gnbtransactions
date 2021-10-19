package com.davidcastella.data.transactions.di

import com.davidcastella.data.conversionrates.datasources.GNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.datasources.remote.RemoteGNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.repositories.ConversionRatesRepositoryImpl
import com.davidcastella.data.transactions.datasources.GNBTransactionsDatasource
import com.davidcastella.data.transactions.datasources.remote.RemoteGNBTransactionsDatasource
import com.davidcastella.data.transactions.repositories.TransactionsRepositoryImpl
import com.davidcastella.data.transactions.repositories.mappers.TransactionResponseModelMapper
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import com.davidcastella.gnb_api.BankServiceAPI
import com.davidcastella.gnb_api.GNBankServiceAPI
import org.koin.dsl.module

val transactionDataModule = module {
    factory<GNBTransactionsDatasource> { RemoteGNBTransactionsDatasource(get()) }
    factory<TransactionsRepository> { TransactionsRepositoryImpl(get(), get()) }
    factory { TransactionResponseModelMapper() }
}