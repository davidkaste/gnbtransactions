package com.davidcastella.domain.conversionrates.di

import com.davidcastella.domain.conversionrates.interactors.GetConversionRates
import org.koin.dsl.module

val conversionRatesDomainModule = module {
    factory { GetConversionRates(get()) }
}