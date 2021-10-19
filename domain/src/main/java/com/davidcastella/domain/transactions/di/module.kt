package com.davidcastella.domain.transactions.di

import com.davidcastella.domain.transactions.interactors.GetTransactions
import org.koin.dsl.module

val transactionsDomainModule = module {
    factory { GetTransactions(get(), get()) }
}