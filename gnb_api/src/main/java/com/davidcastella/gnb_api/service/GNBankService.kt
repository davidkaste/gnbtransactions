package com.davidcastella.gnb_api.service

import com.davidcastella.data.api.BankService
import com.davidcastella.data.api.models.ConversionRateResponseModel
import com.davidcastella.data.api.models.TransactionResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface GNBankService : BankService {
    @GET("/transactions")
    @Headers("Accept: application/json")
    override suspend fun getTransactions(): List<TransactionResponseModel>

    @GET("/rates")
    @Headers("Accept: application/json")
    override suspend fun getCurrencyRates(): List<ConversionRateResponseModel>

    companion object {
        private const val BASE_URL = "https://quiet-stone-2094.herokuapp.com/"

        @JvmStatic
        fun create(): GNBankService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GNBankService::class.java)
    }
}