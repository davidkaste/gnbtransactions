package com.davidcastella.gnbtransactions.di

import com.davidcastella.data.conversionrates.di.conversionRatesDataModule
import com.davidcastella.data.transactions.di.transactionDataModule
import com.davidcastella.domain.conversionrates.di.conversionRatesDomainModule
import com.davidcastella.domain.transactions.di.transactionsDomainModule
import com.davidcastella.features.productlist.di.productListModule
import com.davidcastella.gnb_api.di.apiModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val mainModule = module {
    loadKoinModules(
        listOf(
            apiModule,
            //data
            conversionRatesDataModule,
            transactionDataModule,
            //domain
            conversionRatesDomainModule,
            transactionsDomainModule,
            //presentation
            productListModule,
        )
    )
}