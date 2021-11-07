package com.davidcastella.data.api

import com.davidcastella.data.api.models.ConversionRateResponseModel
import com.davidcastella.data.api.models.TransactionResponseModel


interface BankService {
    suspend fun getTransactions(): List<TransactionResponseModel>
    suspend fun getCurrencyRates(): List<ConversionRateResponseModel>
}