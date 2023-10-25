package com.davidcastella.data.transactions.di

import com.davidcastella.data.transactions.datasources.remote.RemoteTransactionsDatasource
import com.davidcastella.data.transactions.datasources.remote.RemoteTransactionsDatasourceImpl
import com.davidcastella.data.transactions.repositories.TransactionsRepositoryImpl
import com.davidcastella.domain.transactions.repositories.TransactionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface TransactionsDataModule {

    @Binds
    fun provideTransactionsDatasource(datasource: RemoteTransactionsDatasourceImpl): RemoteTransactionsDatasource

    @Binds
    fun provideTransactionsRepo(repository: TransactionsRepositoryImpl): TransactionsRepository
}
