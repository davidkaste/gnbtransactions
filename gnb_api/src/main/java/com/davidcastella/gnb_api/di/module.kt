package com.davidcastella.gnb_api.di

import com.davidcastella.gnb_api.BankServiceAPI
import com.davidcastella.gnb_api.GNBankServiceAPI
import org.koin.dsl.module

val apiModule = module {
    single<BankServiceAPI> { GNBankServiceAPI() }
}