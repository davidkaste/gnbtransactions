package com.davidcastella.data.transactions.di

import com.davidcastella.data.transactions.datasources.GNBTransactionsDatasource
import com.davidcastella.data.transactions.datasources.remote.RemoteGNBTransactionsDatasource
import com.davidcastella.data.transactions.repositories.TransactionsRepositoryImpl
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionsDataModule {

    @Binds
    abstract fun provideTransactionsDatasource(datasource: RemoteGNBTransactionsDatasource): GNBTransactionsDatasource

    @Binds
    abstract fun provideTransactionsRepo(repository: TransactionsRepositoryImpl): TransactionsRepository
}