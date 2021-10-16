package com.davidcastella.gnb_api.service

import retrofit2.http.GET
import com.davidcastella.gnb_api.models.TransactionResponseModel
import com.davidcastella.gnb_api.models.ConversionRateResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers

interface GNBankService {
    @GET("/transactions")
    @Headers("Accept: application/json")
    suspend fun getTransactions(): List<TransactionResponseModel>

    @GET("/rates")
    @Headers("Accept: application/json")
    suspend fun getCurrencyRates(): List<ConversionRateResponseModel>

    companion object {
        private const val BASE_URL = "http://quiet-stone-2094.herokuapp.com/"

        @JvmStatic
        fun create(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}