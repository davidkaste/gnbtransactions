package com.davidcastella.data.conversionrates.di

import com.davidcastella.data.conversionrates.datasources.GNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.datasources.remote.RemoteGNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.repositories.ConversionRatesRepositoryImpl
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ConversionRatesDataModule {

    @Binds
    fun provideConversionRatesDatasource(datasource: RemoteGNBConversionRatesDatasource): GNBConversionRatesDatasource

    @Binds
    fun provideConversionRatesRepo(repository: ConversionRatesRepositoryImpl): ConversionRatesRepository
}
