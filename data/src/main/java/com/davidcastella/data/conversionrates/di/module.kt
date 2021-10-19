package com.davidcastella.data.conversionrates.di

import com.davidcastella.data.conversionrates.datasources.GNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.datasources.remote.RemoteGNBConversionRatesDatasource
import com.davidcastella.data.conversionrates.repositories.ConversionRatesRepositoryImpl
import com.davidcastella.data.conversionrates.repositories.mappers.ConversionRateResponseModelMapper
import com.davidcastella.domain.conversionrates.repositories.ConversionRatesRepository
import org.koin.dsl.module

val conversionRatesDataModule = module {
    factory<GNBConversionRatesDatasource> { RemoteGNBConversionRatesDatasource(get()) }
    factory<ConversionRatesRepository> { ConversionRatesRepositoryImpl(get(), get()) }
    factory { ConversionRateResponseModelMapper() }
}