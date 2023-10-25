package com.davidcastella.data.transactions.datasources.remote.mappers

import com.davidcastella.data.api.models.TransactionResponseModel
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
