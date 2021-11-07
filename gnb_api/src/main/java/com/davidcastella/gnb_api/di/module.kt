package com.davidcastella.gnb_api.di

import com.davidcastella.data.api.BankService
import com.davidcastella.gnb_api.service.GNBankService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideService(): BankService = GNBankService.create()
}