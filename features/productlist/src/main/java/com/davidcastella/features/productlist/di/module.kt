package com.davidcastella.features.productlist.di

import com.davidcastella.features.productlist.mappers.TransactionListMapper
import com.davidcastella.features.productlist.viewmodels.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productListModule = module {
    viewModel { ProductListViewModel(get(), get()) }
    factory { TransactionListMapper() }
}