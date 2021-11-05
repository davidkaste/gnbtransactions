package com.davidcastella.gnb_api.di

import com.davidcastella.gnb_api.BankServiceAPI
import com.davidcastella.gnb_api.GNBankServiceAPI
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    abstract fun provideService(service: GNBankServiceAPI): BankServiceAPI
}