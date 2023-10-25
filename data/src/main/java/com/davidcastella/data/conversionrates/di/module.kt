package com.davidcastella.data.conversionrates.di

import com.davidcastella.data.conversionrates.datasources.remote.RemoteConversionRatesDatasource
import com.davidcastella.data.conversionrates.datasources.remote.RemoteConversionRatesDatasourceImpl
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
    fun provideConversionRatesDatasource(datasource: RemoteConversionRatesDatasourceImpl): RemoteConversionRatesDatasource

    @Binds
    fun provideConversionRatesRepo(repository: ConversionRatesRepositoryImpl): ConversionRatesRepository
}
