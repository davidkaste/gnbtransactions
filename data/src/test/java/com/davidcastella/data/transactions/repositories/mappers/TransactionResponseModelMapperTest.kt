package com.davidcastella.data.transactions.repositories.mappers

import com.davidcastella.gnb_api.models.TransactionResponseModel
import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionResponseModelMapperTest {

    private val mapper = TransactionResponseModelMapper()

    private val transactionResponseModel = TransactionResponseModel("skuX", 32.3, "EUR")

    @Test
    operator fun invoke() {
        val result = mapper(transactionResponseModel)

        assertEquals(transactionResponseModel.sku, result.sku)
        assertEquals(transactionResponseModel.amount.toBigDecimal(), result.amount)
        assertEquals(transactionResponseModel.currency, result.currency)
    }
}